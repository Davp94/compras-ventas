package com.blumbit.compras_ventas.service.spec;

import java.util.List;

import com.blumbit.compras_ventas.dto.request.UsuarioRequest;
import com.blumbit.compras_ventas.dto.response.UsuarioResponse;


public interface UsuarioService {
    List<UsuarioResponse> findAllUsuarios();

    UsuarioResponse findUsuarioById(Long id);

    UsuarioResponse crearUsuario(UsuarioRequest sucursal);

    UsuarioResponse updateUsuario(Long id, UsuarioRequest sucursal);

    UsuarioResponse enableUsuarioById(Long id);

    UsuarioResponse deleteUsuarioById(Long id);
}
