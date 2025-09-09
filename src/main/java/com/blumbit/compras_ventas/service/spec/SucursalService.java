package com.blumbit.compras_ventas.service.spec;

import java.util.List;

import com.blumbit.compras_ventas.dto.response.AlmacenResponse;
import com.blumbit.compras_ventas.entity.Sucursal;

public interface SucursalService {

    List<Sucursal> findAllSucursales();

    List<Sucursal> findByNombreSucursal(String nombre);

    Sucursal findSucursalById(Long id);

    Sucursal crearSucursal(Sucursal sucursal);

    Sucursal updateSucursal(Long id, Sucursal sucursal);

    void deleteSucursalById(Long id);

    List<AlmacenResponse> getAlmacenesBySucursal(Long id);

}
