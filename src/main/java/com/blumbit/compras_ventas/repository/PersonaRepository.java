package com.blumbit.compras_ventas.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.Persona;

public interface PersonaRepository extends ListCrudRepository<Persona, Long>{

}
