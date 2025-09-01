package com.blumbit.compras_ventas.dto.request;

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
public class RolRequest {

    private String nombre;

    private String descripcion;

    private List<Short> permisos;

    public static Rol toEntity(RolRequest rolRequest){
        return Rol.builder()
            .descripcion(rolRequest.getDescripcion())
            .nombre(rolRequest.getNombre())
            .build();
    } 
}
