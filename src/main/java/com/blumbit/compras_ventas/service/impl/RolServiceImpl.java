package com.blumbit.compras_ventas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.blumbit.compras_ventas.dto.request.RolRequest;
import com.blumbit.compras_ventas.dto.response.RolDetailResponse;
import com.blumbit.compras_ventas.dto.response.RolResponse;
import com.blumbit.compras_ventas.entity.Rol;
import com.blumbit.compras_ventas.repository.PermisoRepository;
import com.blumbit.compras_ventas.repository.RolRepository;
import com.blumbit.compras_ventas.service.spec.RolService;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    public RolServiceImpl(RolRepository rolRepository, PermisoRepository permisoRepository) {
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public List<RolResponse> findAllRoles() {
        return rolRepository.findAll().stream()
        .map(rol->RolResponse.fromEntity(rol)).collect(Collectors.toList());
    }

    @Override
    public RolDetailResponse findRolById(Short id) {
        try {
            Rol rolRetrieved = rolRepository.findById(id).orElseThrow(()->new RuntimeException("No se encuentra el rol solicitado"));
            return RolDetailResponse.fromEntity(rolRetrieved, rolRetrieved.getPermisos().stream().map(per->per.getId()).toList());
        } catch (Exception e) {
            throw e;
        }  
    }

    @Override
    public RolResponse crearRol(RolRequest rolRequest) {
        try {
            Rol rolToCreate = RolRequest.toEntity(rolRequest);
            rolToCreate.setPermisos(rolRequest.getPermisos().stream()
            .map(perId-> permisoRepository.findById(perId).orElseThrow(() -> new RuntimeException("Non se encuentra el permiso solicitado"))).collect(Collectors.toSet()));
            return RolResponse.fromEntity(rolRepository.save(rolToCreate));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public RolResponse updateRol(Short id, RolRequest rolRequest) {
        try {
            Rol rolRetrieved = rolRepository.findById(id).orElseThrow(()->new RuntimeException("No se encuentra el rol solicitado"));
            rolRetrieved.setNombre(rolRequest.getNombre());
            rolRetrieved.setDescripcion(rolRequest.getDescripcion());
            rolRetrieved.setPermisos(rolRequest.getPermisos().stream()
            .map(perId-> permisoRepository.findById(perId).orElseThrow(() -> new RuntimeException("Non se encuentra el permiso solicitado"))).collect(Collectors.toSet()));
            return RolResponse.fromEntity(rolRepository.save(rolRetrieved));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteRolById(Short id) {
        rolRepository.deleteById(id);
    }

}
