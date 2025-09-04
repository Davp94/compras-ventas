package com.blumbit.compras_ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.compras_ventas.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
