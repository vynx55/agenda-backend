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
    private String servicio;
    private String fecha;
    private String hora;
    private Double precio;
    private String observaciones;
    private String estado;

    // 🧠 Datos del usuario que creó la cita
    private String usuarioUsername;
    private String usuarioNombre;
    private String usuarioCorreo;
    private String usuarioTelefono;

}
