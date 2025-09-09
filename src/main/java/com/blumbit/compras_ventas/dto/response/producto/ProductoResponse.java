package com.blumbit.compras_ventas.dto.response.producto;

import java.math.BigDecimal;

import com.blumbit.compras_ventas.entity.Producto;

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
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String marca;
    private BigDecimal precioVentaActual;
    private String nombreCategoria;

    public static ProductoResponse fromEntity(Producto producto){
        return ProductoResponse.builder()
            .id(producto.getId())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .codigoBarra(producto.getCodigoBarra())
            .marca(producto.getMarca())
            .precioVentaActual(producto.getPrecioVentaActual())
            .nombreCategoria(producto.getCategoria().getNombre())
            .build();
    }

}
