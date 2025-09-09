package com.blumbit.compras_ventas.dto.request;

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
public class ProductoAlmacenRequest {
    private Long almacenId;
    private Long productoId;
    private Integer stock;
}
