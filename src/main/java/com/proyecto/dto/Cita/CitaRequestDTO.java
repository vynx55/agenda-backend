package com.proyecto.dto.Cita;

import lombok.Data;

@Data
public class CitaRequestDTO {
    private String fecha;
    private String hora;
    private String servicio;
    private Double precio;
    private String observaciones;
    private String estado;
    private Long empleadoId;
}
