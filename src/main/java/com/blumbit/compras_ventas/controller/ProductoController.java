package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.common.dto.PageableRequest;
import com.blumbit.compras_ventas.common.dto.PageableResponse;
import com.blumbit.compras_ventas.dto.request.ProductoAlmacenRequest;
import com.blumbit.compras_ventas.dto.request.ProductoRequest;
import com.blumbit.compras_ventas.dto.response.producto.ProductoFilterCriteria;
import com.blumbit.compras_ventas.dto.response.producto.ProductoResponse;
import com.blumbit.compras_ventas.service.spec.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/paginacion")
    public PageableResponse<ProductoResponse> getProductosPaginacion(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false) String filterValue,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String codigoBarra,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Long almacenId,
            @RequestParam(required = false) String nombreCategoria) {

        ProductoFilterCriteria criteria = ProductoFilterCriteria.builder()
                .almacenId(almacenId)
                .codigoBarra(codigoBarra)
                .descripcion(descripcion)
                .marca(marca)
                .nombre(nombre)
                .nombreCategoria(nombreCategoria)
                .build();
        PageableRequest<ProductoFilterCriteria> request = PageableRequest.<ProductoFilterCriteria>builder()
                .criterials(criteria)
                .filterValue(filterValue)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .sortField(sortField)
                .sortOrder(sortOrder)
                .build();
        return productoService.getProductsPagination(request);
    }

    @PostMapping
    public ProductoResponse createProducto(@ModelAttribute ProductoRequest productoRequest) {
        return productoService.createProducto(productoRequest);
    }

    @PostMapping("/almacen")
    public ProductoResponse createProductoAlmacen(@RequestBody ProductoAlmacenRequest productoAlmacenRequest) {
        return productoService.createProductoAlmacen(productoAlmacenRequest);
    }

}
