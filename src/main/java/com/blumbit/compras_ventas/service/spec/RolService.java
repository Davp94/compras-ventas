package com.blumbit.compras_ventas.service.spec;

import java.util.List;

import com.blumbit.compras_ventas.dto.request.RolRequest;
import com.blumbit.compras_ventas.dto.response.RolDetailResponse;
import com.blumbit.compras_ventas.dto.response.RolResponse;

public interface RolService {

    List<RolResponse> findAllRoles();

    RolDetailResponse findRolById(Short id);

    RolResponse crearRol(RolRequest rolRequest);

    RolResponse updateRol(Short id, RolRequest rolRequest);

    void deleteRolById(Short id);
}
