package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Empleado;
import com.proyecto.entity.EstadoCita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto, Empleado empleado) {
        if (dto == null) return null;

        Cita cita = new Cita();
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setServicio(dto.getServicio());
        cita.setPrecio(dto.getPrecio());
        cita.setObservaciones(dto.getObservaciones());
        cita.setEmpleado(empleado);
        // Estado predeterminado: PENDIENTE
        cita.setEstado(EstadoCita.PENDIENTE);

        return cita;
    }

    public CitaResponseDTO toResponse(Cita cita) {
        if (cita == null) return null;

        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(cita.getId());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setServicio(cita.getServicio());
        dto.setPrecio(cita.getPrecio());
        dto.setObservaciones(cita.getObservaciones());
        dto.setEstado(cita.getEstado());

        // Datos del usuario
        if (cita.getUsuario() != null) {
            dto.setUsuarioUsername(cita.getUsuario().getUsername());
        }

        // Datos del barbero (empleado)
        if (cita.getEmpleado() != null) {
            dto.setEmpleadoId(cita.getEmpleado().getId());
            dto.setEmpleadoNombre(cita.getEmpleado().getNombre());
            dto.setEmpleadoFotoUrl(cita.getEmpleado().getFotoUrl());
        }

        return dto;
    }

}
