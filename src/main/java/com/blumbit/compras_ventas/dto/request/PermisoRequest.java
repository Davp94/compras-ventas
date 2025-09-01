package com.blumbit.compras_ventas.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class PermisoRequest {

    @Size(max = 20, message = "el nombre puede tener una longitud maxima de {max} caracteres")
    private String name;

    @Pattern(regexp = "^[a-z]{8,16}$", message = "El campo debe tener entre 8-16 caracteres y solo minusculas")
    private String resource;

    private String action;

    private String detail;

}
