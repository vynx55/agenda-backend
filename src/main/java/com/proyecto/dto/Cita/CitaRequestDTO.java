package com.proyecto.dto.Cita;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String fecha;
    private String hora;
    private String observaciones;
    private String estado;
}
