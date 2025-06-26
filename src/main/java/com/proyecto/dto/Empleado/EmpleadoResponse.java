package com.proyecto.dto.Empleado;

import lombok.Data;

@Data
public class EmpleadoResponse {
    private Long id;
    private String nombre;
    private String especialidad;
    private String fotoUrl;
}
