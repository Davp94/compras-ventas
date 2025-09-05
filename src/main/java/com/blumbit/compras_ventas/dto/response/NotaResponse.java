package com.blumbit.compras_ventas.dto.response;

import java.time.LocalDateTime;

import com.blumbit.compras_ventas.entity.Nota;

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
public class NotaResponse {
    private Long id;
    private LocalDateTime fechaEmision;
    private String observaciones;
    private String tipoNota;
    private String estadoNota;

    public static NotaResponse fromEntity(Nota nota){
        return NotaResponse.builder()
        .id(nota.getId())
        .fechaEmision(nota.getFecha())
        .observaciones(nota.getObservaciones())
        .tipoNota(nota.getTipoNota())
        .estadoNota(nota.getEstadoNota())    
        .build();
    }

}
