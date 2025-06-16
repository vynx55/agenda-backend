package com.proyecto.dto.Cita;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CitaResponseDTO {
    private Long id;
    private String nombreCliente;
    private String correo;
    private String telefono;
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
