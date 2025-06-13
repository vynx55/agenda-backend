package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto){
        return Cita.builder()
                .nombreCliente(dto.getNombreCliente())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .servicio(dto.getServicio())
                .precio(dto.getPrecio())
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .observaciones(dto.getObservaciones())
                .estado(dto.getEstado())
                .build();
    }

    public CitaResponseDTO toResponse(Cita entity){
        return CitaResponseDTO.builder()
                .id(entity.getId())
                .nombreCliente(entity.getNombreCliente())
                .telefono(entity.getTelefono())
                .correo(entity.getCorreo())
                .servicio(entity.getServicio())
                .precio(entity.getPrecio())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado())
                .build();
    }
}
