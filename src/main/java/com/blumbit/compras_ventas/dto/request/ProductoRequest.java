package com.blumbit.compras_ventas.dto.request;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.blumbit.compras_ventas.entity.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private String marca;
    private BigDecimal precioVentaActual;
    private MultipartFile image;
    private Long categoriaId;

    public static Producto toEntity(ProductoRequest productoRequest){
        return Producto.builder()
        .nombre(productoRequest.getNombre())
        .descripcion(productoRequest.getDescripcion())
        .unidadMedida(productoRequest.getUnidadMedida())
        .marca(productoRequest.getMarca())
        .precioVentaActual(productoRequest.getPrecioVentaActual())
        .build();
    }
}
