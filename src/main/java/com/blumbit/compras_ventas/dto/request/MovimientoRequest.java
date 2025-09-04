package com.blumbit.compras_ventas.dto.request;

import java.math.BigDecimal;

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
public class MovimientoRequest {
    private Integer cantidad;
    private String tipoMovimiento;
    private BigDecimal precioUnitarioCompra;
    private BigDecimal precioUnitarioVenta;
    private String observaciones;
    private Long almacenId;
    private Long productoId;
    private Long notaId;
}
