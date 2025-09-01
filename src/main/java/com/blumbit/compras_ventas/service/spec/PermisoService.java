package com.blumbit.compras_ventas.service.spec;

import java.util.List;

import com.blumbit.compras_ventas.dto.request.PermisoRequest;
import com.blumbit.compras_ventas.dto.response.PermisoResponse;
import com.blumbit.compras_ventas.entity.Permiso;

public interface PermisoService {

    List<PermisoResponse> findAllPermisos();

    PermisoResponse findPermisoById(Short id);

    PermisoResponse crearPermiso(PermisoRequest permisoRequest);

    PermisoResponse updatePermiso(Short id, PermisoRequest permisoRequest);

    void deletePermisoById(Short id);

}
