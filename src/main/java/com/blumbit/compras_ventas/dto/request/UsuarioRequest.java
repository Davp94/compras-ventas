package com.blumbit.compras_ventas.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.blumbit.compras_ventas.entity.Persona;
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
public class UsuarioRequest {
    private String nombres;

    private String apellidos;

    private LocalDate fechaNacimiento;

    private String genero;

    private String telefono;

    private String direccion;

    private String documentoIdentidad;

    private String tipoDocumento;
    
    private String nacionalidad;

    private String correo;

    private String password;

    private List<Short> roles;

    public static Persona toEntityPersona(UsuarioRequest usuarioRequest){
        return Persona.builder()
                .nombres(usuarioRequest.getNombres())
                .apellidos(usuarioRequest.getApellidos())
                .fechaNacimiento(usuarioRequest.getFechaNacimiento())
                .genero(usuarioRequest.getGenero())
                .telefono(usuarioRequest.getTelefono())
                .direccion(usuarioRequest.getDireccion())
                .documentoIdentidad(usuarioRequest.getDocumentoIdentidad())
                .tipoDocumento(usuarioRequest.getTipoDocumento())
                .nacionalidad(usuarioRequest.getNacionalidad())
                .build();
    }

    public static Usuario toEntityUsuario(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .email(usuarioRequest.getCorreo())
                .password(usuarioRequest.getPassword())
                .build();
    }
}
