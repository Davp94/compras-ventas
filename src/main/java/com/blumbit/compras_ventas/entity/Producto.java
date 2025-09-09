package com.blumbit.compras_ventas.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
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
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String unidadMedida;
    private String marca;
    private BigDecimal precioVentaActual;
    private String estado;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
