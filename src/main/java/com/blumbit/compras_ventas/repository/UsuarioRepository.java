package com.blumbit.compras_ventas.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import com.blumbit.compras_ventas.entity.Usuario;

public interface UsuarioRepository extends ListCrudRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);
}
