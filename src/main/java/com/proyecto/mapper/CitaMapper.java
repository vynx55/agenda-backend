package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto) {
        Cita cita = new Cita();
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setServicio(dto.getServicio());
        cita.setPrecio(dto.getPrecio());
        cita.setObservaciones(dto.getObservaciones());
        cita.setEstado(dto.getEstado());
        return cita;
    }

    public CitaResponseDTO toResponse(Cita cita) {
        CitaResponseDTO dto = new CitaResponseDTO();
        dto.setId(cita.getId());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setServicio(cita.getServicio());
        dto.setPrecio(cita.getPrecio());
        dto.setObservaciones(cita.getObservaciones());
        dto.setEstado(cita.getEstado());

        if (cita.getUsuario() != null) {
            dto.setUsuarioUsername(cita.getUsuario().getUsername());
        }

        return dto;
    }
}

