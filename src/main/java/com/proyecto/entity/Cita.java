package com.proyecto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private String telefono;
    private String correo;
    private String servicio;
    private Double precio;
    private String fecha;
    private String hora;
    private String observaciones;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}