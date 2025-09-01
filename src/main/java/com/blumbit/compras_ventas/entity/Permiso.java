package com.blumbit.compras_ventas.entity;

import java.util.Set;

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
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Short id; //10000000 aprox.

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "resource", length = 100)
    private String resource;

    @Column(name = "action", length = 100)
    private String action;

    @Column(name = "detail")
    private String detail;

    @ManyToMany(mappedBy = "permisos")
    private Set<Rol> roles;
}