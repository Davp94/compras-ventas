package com.blumbit.compras_ventas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.service.spec.SucursalService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    private final SucursalService sucursalService;

    //Alternativa inyeccion de dependencias
    // @Autowired
    // private SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService; 
    }
    
    @GetMapping
    public List<Sucursal> findAllSucursales() {
        return sucursalService.findAllSucursales();
    }

    @GetMapping("/{id}")
    public Sucursal findSucursalById(@PathVariable Long id) {
        return sucursalService.findSucursalById(id);
    }

    @GetMapping("/search")
    public List<Sucursal> findAllSucursalesByNombre(@RequestParam String nombre) {
        return sucursalService.findByNombreSucursal(nombre);
    }

    @PostMapping
    public Sucursal createSucrusal(@RequestBody Sucursal sucursal) {

        return sucursalService.crearSucursal(sucursal);
    }

    @PutMapping("/{id}")
    public Sucursal updateSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        return sucursalService.updateSucursal(id, sucursal);
    }

    @DeleteMapping("/{id}")
    public void deleteSucursal(@PathVariable Long id){
        sucursalService.deleteSucursalById(id);
    }
}
