package com.blumbit.compras_ventas.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.blumbit.compras_ventas.entity.Usuario;

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
public class UsuarioResponse {
    private String correo;
    private String username;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String documentoIdentidad;
    private String estado;
    private List<Short> roles;

    public static UsuarioResponse fromEntity(Usuario usuario){
        return UsuarioResponse.builder()
                .correo(usuario.getEmail())
                .username(usuario.getName())
                .nombres(usuario.getPersona().getNombres())
                .apellidos(usuario.getPersona().getApellidos())
                .telefono(usuario.getPersona().getTelefono())
                .estado(usuario.getEstado())
                .documentoIdentidad(usuario.getPersona().getDocumentoIdentidad())
                .roles(usuario.getRoles().stream().map(rol->rol.getId()).collect(Collectors.toList()))
                .build();
    }
}
