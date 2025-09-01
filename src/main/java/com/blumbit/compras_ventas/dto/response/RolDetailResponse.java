package com.blumbit.compras_ventas.dto.response;

import java.util.List;

import com.blumbit.compras_ventas.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolDetailResponse {

     private Short id;

    private String nombre;
    
    private String descripcion;

    private List<Short> permisos;

    public static RolDetailResponse fromEntity(Rol rol, List<Short> permisosIds){
        return RolDetailResponse.builder()
            .id(rol.getId())
            .descripcion(rol.getDescripcion())
            .nombre(rol.getNombre())
            .permisos(permisosIds)
            .build();
    } 
}
