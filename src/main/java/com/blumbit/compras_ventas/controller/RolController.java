package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.dto.response.RolDetailResponse;
import com.blumbit.compras_ventas.dto.response.RolResponse;
import com.blumbit.compras_ventas.service.spec.RolService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/{id}")
    public RolDetailResponse getMethodName(@PathVariable Short id) {
        return rolService.findRolById(id);
    }
    

}
