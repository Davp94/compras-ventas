package com.blumbit.compras_ventas.dto.response.producto;

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
public class ProductoFilterCriteria {
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String marca;
    private Long almacenId;
    private String nombreCategoria;

}
