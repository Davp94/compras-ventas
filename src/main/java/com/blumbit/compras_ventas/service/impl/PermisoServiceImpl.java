package com.blumbit.compras_ventas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.request.PermisoRequest;
import com.blumbit.compras_ventas.dto.response.PermisoResponse;
import com.blumbit.compras_ventas.entity.Permiso;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.service.spec.PermisoService;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    public List<PermisoResponse> findAllPermisos() {
        List<PermisoResponse> permisosResponse = new ArrayList();
        List<Permiso> permisosRetrieved = permisoRepository.findAll();
        permisosRetrieved.forEach(permiso -> permisosResponse.add(PermisoResponse.builder()
                .action(permiso.getAction())
                .name(permiso.getName())
                .detail(permiso.getDetail())
                .resource(permiso.getResource())
                .id(permiso.getId())
                .build()));
        return permisosResponse;
    }

    @Override
    public PermisoResponse findPermisoById(Short id) {
        Permiso permiso = permisoRepository.findById(id).orElse(null);
        if (permiso != null) {
            return PermisoResponse.builder()
                    .action(permiso.getAction())
                    .name(permiso.getName())
                    .detail(permiso.getDetail())
                    .resource(permiso.getResource())
                    .id(permiso.getId())
                    .build();
        } else {
            return null;
        }
    }

    @Override
    public PermisoResponse crearPermiso(PermisoRequest permisoRequest) {
        Permiso permisoToCreate = Permiso.builder()
            .action(permisoRequest.getAction())
            .detail(permisoRequest.getDetail())
            .name(permisoRequest.getName())
            .resource(permisoRequest.getResource())
            .build();
        Permiso permisoCreated = permisoRepository.save(permisoToCreate);
        return PermisoResponse.builder()
        .action(permisoCreated.getAction())
                    .name(permisoCreated.getName())
                    .detail(permisoCreated.getDetail())
                    .resource(permisoCreated.getResource())
                    .id(permisoCreated.getId())
        .build();
    }

    @Override
    public PermisoResponse updatePermiso(Short id, PermisoRequest permisoRequest) {
        Permiso permisoRetrieved = permisoRepository.findById(id).orElse(null);
        if (permisoRetrieved != null) {
            permisoRetrieved.setAction(permisoRequest.getAction());
            permisoRetrieved.setDetail(permisoRequest.getDetail());
            permisoRetrieved.setName(permisoRequest.getName());
            permisoRetrieved.setResource(permisoRequest.getResource());

            Permiso permisoUpdated = permisoRepository.save(permisoRetrieved);
            return PermisoResponse.builder()
                    .action(permisoUpdated.getAction())
                    .name(permisoUpdated.getName())
                    .detail(permisoUpdated.getDetail())
                    .resource(permisoUpdated.getResource())
                    .id(permisoUpdated.getId())
                    .build();
        }else {
            return null;
        }
    }

    @Override
    public void deletePermisoById(Short id) {
        permisoRepository.deleteById(id);
    }

}
