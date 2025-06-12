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
public class CitaResponseDTO {
    private Long id;
    private String nombreCliente;
    private String telefono;
    private String servicio;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
}
