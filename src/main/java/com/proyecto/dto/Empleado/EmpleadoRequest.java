package com.proyecto.dto.Empleado;

import lombok.Data;

@Data
public class EmpleadoRequest {
    private String nombre;
    private String especialidad;
    private String fotoUrl;
}
