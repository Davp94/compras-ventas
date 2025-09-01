package com.blumbit.compras_ventas.dto.response;

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
public class PermisoResponse {
    
    private Short id;
    
    private String name;

    private String resource;

    private String action;

    private String detail;
}
