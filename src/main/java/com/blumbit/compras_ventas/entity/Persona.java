package com.blumbit.compras_ventas.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "per_nombres", length = 100, nullable = false)
    private String nombres; // nombres != per_nombres

    @Column(name = "apellidos", length = 100, nullable = false)
    private String apellidos; // per_apellidos

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento; //fecha_nacimiento

    private String genero;

    private String telefono;

    private String direccion;

    private String documentoIdentidad; // documento_identidad

    private String tipoDocumento;
    
    private String nacionalidad;

    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
