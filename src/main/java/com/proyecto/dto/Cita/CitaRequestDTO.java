package com.proyecto.dto.Cita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaRequestDTO {

    private String servicio;
    private Double precio;
    private String fecha;
    private String hora;
    private String observaciones;
    private String estado;

}
