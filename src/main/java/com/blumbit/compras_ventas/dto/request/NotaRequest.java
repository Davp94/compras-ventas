package com.blumbit.compras_ventas.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.blumbit.compras_ventas.entity.Nota;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotaRequest {

    private String tipoNota;
    private BigDecimal impuestos;
    private BigDecimal descuento;
    private BigDecimal totalCalculado;
    private String observaciones;
    private Long clienteId;
    private Long usuarioId;
    private List<MovimientoRequest> movimientos;

    public static Nota toEntity(NotaRequest notaRequest){
        return Nota.builder()
            .descuento(notaRequest.getDescuento())
            .tipoNota(notaRequest.getTipoNota())
            .impuestos(notaRequest.getImpuestos())
            .totalCalculado(notaRequest.getTotalCalculado())
            .observaciones(notaRequest.getObservaciones())
            .build();
    }
}
