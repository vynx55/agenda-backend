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

        // Solo agregar info del usuario si el rol es ADMIN
        var auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ROLE_ADMIN"));

        if (isAdmin) {
            builder
                    .usuarioUsername(entity.getUsuario().getUsername())
                    .usuarioNombre(entity.getUsuario().getNombre())
                    .usuarioCorreo(entity.getUsuario().getCorreo())
                    .usuarioTelefono(entity.getUsuario().getTelefono());
        }

        return builder.build();
    }
}