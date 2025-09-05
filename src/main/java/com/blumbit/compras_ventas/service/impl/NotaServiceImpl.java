package com.blumbit.compras_ventas.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.compras_ventas.dto.request.MovimientoRequest;
import com.blumbit.compras_ventas.dto.request.NotaRequest;
import com.blumbit.compras_ventas.dto.response.NotaResponse;
import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Cliente;
import com.blumbit.compras_ventas.entity.Movimiento;
import com.blumbit.compras_ventas.entity.Nota;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.entity.Usuario;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.MovimientoRepository;
import com.blumbit.compras_ventas.repository.NotaRepository;
import com.blumbit.compras_ventas.service.spec.NotaService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final MovimientoRepository movimientoRepository;
    private final AlmacenProductoRepository almacenProductoRepository;
    private final EntityManager entityManager;
    
    @Override
    public List<NotaResponse> findAllNotas() {
        try {
            return notaRepository.findAll().stream().map(nota -> NotaResponse.fromEntity(nota))
            .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public NotaResponse createNota(NotaRequest notaRequest) {
        try {
            //TODO VALID CALCULATED TOTAL
            //CREATE NOTA
            Nota notaToCreate = NotaRequest.toEntity(notaRequest);
            notaToCreate.setCliente(entityManager.getReference(Cliente.class, notaRequest.getClienteId()));
            notaToCreate.setUsuario(entityManager.getReference(Usuario.class, notaRequest.getUsuarioId()));
            notaToCreate.setEstadoNota("ACTIVO");
            notaToCreate.setFecha(LocalDateTime.now());
            Nota notaCreated = notaRepository.save(notaToCreate);
            //CREATE MOVIMIENTOS & validate stock
            List<Movimiento> movimientosSaved = new ArrayList<>();
            for(MovimientoRequest movimientoRequest : notaRequest.getMovimientos()){
                if(validStock(movimientoRequest)){
                    Movimiento movimientoToCreate = MovimientoRequest.toEntity(movimientoRequest);
                    movimientoToCreate.setAlmacen(entityManager.getReference(Almacen.class, movimientoRequest.getAlmacenId()));
                    movimientoToCreate.setNota(notaCreated);
                    movimientoToCreate.setProducto(entityManager.getReference(Producto.class, movimientoRequest.getProductoId()));
                    movimientosSaved.add(movimientoRepository.save(movimientoToCreate));
                }else {
                    throw new RuntimeException("error al validar stock");
                }
            }
            //UPDATE STOCK
            for (Movimiento movimiento : movimientosSaved){
                AlmacenProducto almacenProductoRetrieved = almacenProductoRepository
                .findByAlmacen_IdAndProducto_Id(movimiento.getAlmacen().getId(), movimiento.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("No se encontro el producto en el almacen"));
                switch (movimiento.getTipoMovimiento()) {
                    case "COMPRA":
                        almacenProductoRetrieved.setCantidad(almacenProductoRetrieved.getCantidad() + movimiento.getCantidad());
                        break;
                    case "VENTA":
                        almacenProductoRetrieved.setCantidad(almacenProductoRetrieved.getCantidad() - movimiento.getCantidad());
                        break;
                    default:
                        break;
                }
                almacenProductoRepository.save(almacenProductoRetrieved);
            }
            return NotaResponse.fromEntity(notaCreated);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean validStock(MovimientoRequest movimientoRequest) {
        try {
            return movimientoRequest.getTipoMovimiento().equals("VENTA")
            && movimientoRequest.getCantidad() <= almacenProductoRepository.findByAlmacen_IdAndProducto_Id(movimientoRequest.getAlmacenId(), movimientoRequest.getProductoId()).get().getCantidad();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto en almacen");
        }
      
    }

}
