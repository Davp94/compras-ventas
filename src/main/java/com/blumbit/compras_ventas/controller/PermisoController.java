package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.request.PermisoRequest;
import com.blumbit.compras_ventas.dto.response.PermisoResponse;
import com.blumbit.compras_ventas.service.spec.PermisoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/permiso")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @GetMapping
    public List<PermisoResponse> findALLPermiso() {
        return permisoService.findAllPermisos();
    }

    @GetMapping("/{id}")
    public PermisoResponse findPermisoById(@PathVariable Short id) {
        return permisoService.findPermisoById(id);
    }

    @PostMapping
    public PermisoResponse createPermiso(@RequestBody @Valid PermisoRequest permisoRequest) {
        return permisoService.crearPermiso(permisoRequest);
    }

    @PutMapping("/{id}")
    public PermisoResponse updatePermiso(@PathVariable Short id, @RequestBody @Valid PermisoRequest permisoRequest) {
        return permisoService.updatePermiso(id, permisoRequest);
    }
    
    @DeleteMapping("/{id}")
    public void deletePermiso(@PathVariable Short id){
        permisoService.deletePermisoById(id);
    }
}
