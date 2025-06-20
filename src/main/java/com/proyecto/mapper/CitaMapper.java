package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita toEntity(CitaRequestDTO dto) {
        return Cita.builder()
                .servicio(dto.getServicio())
                .precio(dto.getPrecio())
                .fecha(dto.getFecha())
                .hora(dto.getHora())
                .observaciones(dto.getObservaciones())
                .estado(dto.getEstado())
                .build();
    }

    public CitaResponseDTO toResponse(Cita cita, boolean isAdmin) {
        Usuario usuario = cita.getUsuario();

        return CitaResponseDTO.builder()
                .id(cita.getId())
                .servicio(cita.getServicio())
                .precio(cita.getPrecio())
                .fecha(cita.getFecha())
                .hora(cita.getHora())
                .observaciones(cita.getObservaciones())
                .estado(cita.getEstado())
                .nombreCliente(isAdmin && usuario != null ? usuario.getNombre() : null)
                .correo(isAdmin && usuario != null ? usuario.getCorreo() : null)
                .telefono(isAdmin && usuario != null ? usuario.getTelefono() : null)
                .build();
    }
}
