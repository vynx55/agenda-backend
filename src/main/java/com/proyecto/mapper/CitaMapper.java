package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto){
        return Cita.builder()
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
                .servicio(entity.getServicio())
                .precio(entity.getPrecio())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado())

                // 👇 Extrae los datos desde el usuario asociado
                .usuarioUsername(entity.getUsuario().getUsername())
                .usuarioNombre(entity.getUsuario().getNombre())
                .usuarioCorreo(entity.getUsuario().getCorreo())
                .usuarioTelefono(entity.getUsuario().getTelefono())

                .build();
    }
}
