package com.blumbit.compras_ventas.dto.response;

import com.blumbit.compras_ventas.entity.Cliente;

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
public class ClienteResponse {
    private Long id;

    private String tipo;

    private String razonSocial;
    
    private String nroIdentificacion;

    public static ClienteResponse fromEntity(Cliente cliente){
        return ClienteResponse.builder()
        .id(cliente.getId())
        .tipo(cliente.getTipo())
        .razonSocial(cliente.getRazonSocial())
        .nroIdentificacion(cliente.getNroIdentificacion())
        .build();
    }
}
