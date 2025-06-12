package com.proyecto.dto.Cita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaRequestDTO {

    private String nombreCliente;
    private String telefono;
    private String correo;
    private String servicio;
    private Double precio;
    private LocalDate fecha;
    private LocalTime hora;
    private String observaciones;
    private String estado;
}
