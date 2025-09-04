package com.blumbit.compras_ventas.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.Cliente;

public interface ClienteRepository extends ListCrudRepository<Cliente, Long>{

}
