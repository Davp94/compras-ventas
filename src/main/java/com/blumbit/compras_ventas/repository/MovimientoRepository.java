package com.blumbit.compras_ventas.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.Movimiento;

public interface MovimientoRepository extends ListCrudRepository<Movimiento, Long>{

}
