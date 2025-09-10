package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.request.UsuarioRequest;
import com.blumbit.compras_ventas.dto.response.UsuarioResponse;
import com.blumbit.compras_ventas.service.spec.UsuarioService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponse> getAllUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponse findUsuarioById(@PathVariable Long id) {
        return usuarioService.findUsuarioById(id);
    }

    @PostMapping
    public UsuarioResponse createUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        return usuarioService.crearUsuario(usuarioRequest);
    }

    @PutMapping("/{id}")
    public UsuarioResponse updateUsuario(@PathVariable Long id, @RequestBody UsuarioRequest entity) {
        return usuarioService.updateUsuario(id, entity);
    }

    @PatchMapping("/{id}")
    public UsuarioResponse enableUsuario(@PathVariable Long id) {
        return usuarioService.enableUsuarioById(id);
    }

    @DeleteMapping("/{id}")
    public UsuarioResponse deleteUsuarioById(@PathVariable Long id){
        return usuarioService.deleteUsuarioById(id);
    }  
}
