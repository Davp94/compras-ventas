package com.blumbit.compras_ventas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.entity.Sucursal;
import com.blumbit.compras_ventas.repository.SucursalRepository;
import com.blumbit.compras_ventas.service.spec.SucursalService;

@Service
public class SucursalServiceImpl implements SucursalService{

    private final SucursalRepository sucursalRepository;

    public SucursalServiceImpl(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public List<Sucursal> findAllSucursales() {
        return sucursalRepository.findAll();
    }

    @Override
    public List<Sucursal> findByNombreSucursal(String nombre) {
        return sucursalRepository.findByNombre(nombre);
    }

    @Override
    public Sucursal findSucursalById(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    @Override
    public Sucursal crearSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal updateSucursal(Long id, Sucursal sucursal) {
       Sucursal sucursalRetrieved = sucursalRepository.findById(id).orElse(null);
       if(sucursalRetrieved != null){
            sucursalRetrieved.setNombre(sucursal.getNombre());
            return sucursalRepository.save(sucursalRetrieved);
       }else {
        return null;
       }
    }

    @Override
    public void deleteSucursalById(Long id) {
        sucursalRepository.deleteById(id);
    }

}
