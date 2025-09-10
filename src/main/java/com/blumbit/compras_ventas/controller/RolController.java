package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.request.RolRequest;
import com.blumbit.compras_ventas.dto.response.RolDetailResponse;
import com.blumbit.compras_ventas.dto.response.RolResponse;
import com.blumbit.compras_ventas.service.spec.RolService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<RolResponse> getAlLRoles() {
        return rolService.findAllRoles();
    }
    

    @GetMapping("/{id}")
    public RolDetailResponse getRolById(@PathVariable Short id) {
        return rolService.findRolById(id);
    }

    @PostMapping
    public RolResponse createRol(@RequestBody RolRequest rolRequest) {
        return rolService.crearRol(rolRequest);
    }

    @PutMapping("/{id}")
    public RolResponse putMethodName(@PathVariable Short id, @RequestBody RolRequest rolRequest) {
        return rolService.updateRol(id, rolRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteRolById(@PathVariable Short id){
        rolService.deleteRolById(id);
    }
    
    

}
