package com.blumbit.compras_ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blumbit.compras_ventas.common.dto.PageableRequest;
import com.blumbit.compras_ventas.common.dto.PageableResponse;
import com.blumbit.compras_ventas.common.dto.file.FileRequest;
import com.blumbit.compras_ventas.common.dto.file.FileResponse;
import com.blumbit.compras_ventas.dto.request.ProductoAlmacenRequest;
import com.blumbit.compras_ventas.dto.request.ProductoRequest;
import com.blumbit.compras_ventas.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.response.producto.ProductoResponse;
import com.blumbit.compras_ventas.entity.Almacen;
import com.blumbit.compras_ventas.entity.AlmacenProducto;
import com.blumbit.compras_ventas.entity.Categoria;
import com.blumbit.compras_ventas.entity.Producto;
import com.blumbit.compras_ventas.repository.AlmacenProductoRepository;
import com.blumbit.compras_ventas.repository.ProductoRepository;
import com.blumbit.compras_ventas.repository.specification.ProductoSpecification;
import com.blumbit.compras_ventas.service.spec.FileService;
import com.blumbit.compras_ventas.service.spec.ProductoService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final AlmacenProductoRepository almacenProductoRepository;

    private final FileService fileService;

    private final EntityManager entityManager;

    @Override
    public PageableResponse<ProductoResponse> getProductsPagination(
            PageableRequest<ProductoFilterCriteria> pageableRequest) {

        Sort sort = pageableRequest.getSortOrder().equalsIgnoreCase("desc")
                ? Sort.by(pageableRequest.getSortField()).descending()
                : Sort.by(pageableRequest.getSortField()).ascending();
        Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sort);
        Specification<Producto> spec = Specification.where(null);
        if (pageableRequest.getCriterials() != null) {
            spec = ProductoSpecification.createSpecification(pageableRequest.getCriterials());
        }
        Page<Producto> productoPage = productoRepository.findAll(spec, pageable);
        return PageableResponse.<ProductoResponse>builder()
                .pageNumber(productoPage.getNumber())
                .totalElements(productoPage.getTotalElements())
                .pageSize(productoPage.getSize())
                .totalPages(productoPage.getTotalPages())
                .content(productoPage.getContent().stream().map(ProductoResponse::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public ProductoResponse createProducto(ProductoRequest productoRequest) {
        try {
            Producto productoToCreate = ProductoRequest.toEntity(productoRequest);
            productoToCreate
                    .setCategoria(entityManager.getReference(Categoria.class, productoRequest.getCategoriaId()));
            productoToCreate.setImageUrl("add image url");
            FileResponse createdFile = fileService.createFile(FileRequest.builder()
                    .file(productoRequest.getImage()).build());
            productoToCreate.setImageUrl(createdFile.getFilePath());
            return ProductoResponse.fromEntity(productoRepository.save(productoToCreate));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ProductoResponse createProductoAlmacen(ProductoAlmacenRequest productoAlmacenRequest) {
        try {
            AlmacenProducto almacenProductoToCreate = AlmacenProducto.builder()
                    .almacen(entityManager.getReference(Almacen.class, productoAlmacenRequest.getAlmacenId()))
                    .producto(entityManager.getReference(Producto.class, productoAlmacenRequest.getProductoId()))
                    .cantidad(productoAlmacenRequest.getStock())
                    .build();
            return ProductoResponse.fromEntity(almacenProductoRepository.save(almacenProductoToCreate).getProducto());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<ProductoResponse> getAllProductosByAlmacen(Short almacenId) {
        return almacenProductoRepository.findByAlmacen_Id(almacenId).stream().map(res -> ProductoResponse.fromEntity(res.getProducto())).toList();
    }

}
