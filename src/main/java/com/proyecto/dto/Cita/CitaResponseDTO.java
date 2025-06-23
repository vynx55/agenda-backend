// CitaResponseDTO.java
package com.proyecto.dto.Cita;

import lombok.Data;

@Data
public class CitaResponseDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String servicio;
    private Double precio;
    private String observaciones;
    private String estado;
    private String usuarioUsername;
}
