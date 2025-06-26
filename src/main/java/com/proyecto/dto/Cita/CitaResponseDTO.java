// CitaResponseDTO.java
package com.proyecto.dto.Cita;

import com.proyecto.entity.EstadoCita;
import lombok.Data;

@Data
public class CitaResponseDTO {
    private Long id;
    private String fecha;
    private String hora;
    private String servicio;
    private Double precio;
    private String observaciones;
    private EstadoCita estado;
    private String usuarioUsername;
    private Long empleadoId;
    private String empleadoNombre;
    private String empleadoFotoUrl;

}
