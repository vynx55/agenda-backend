package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public CitaResponseDTO toResponse(Cita entity) {
        CitaResponseDTO.CitaResponseDTOBuilder builder = CitaResponseDTO.builder()
                .id(entity.getId())
                .servicio(entity.getServicio())
                .precio(entity.getPrecio())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado());

        // Solo agregar datos del usuario si el que accede es ADMIN
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"))) {

            builder.usuarioUsername(entity.getUsuario().getUsername());
            builder.usuarioNombre(entity.getUsuario().getNombre());
            builder.usuarioCorreo(entity.getUsuario().getCorreo());
            builder.usuarioTelefono(entity.getUsuario().getTelefono());
        }

        return builder.build();
    }
}
