package com.proyecto.mapper;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import org.springframework.security.core.Authentication;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;

        if (auth != null && auth.isAuthenticated()) {
            isAdmin = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));
        }

        Usuario usuario = entity.getUsuario();

        return CitaResponseDTO.builder()
                .id(entity.getId())
                .servicio(entity.getServicio())
                .precio(entity.getPrecio())
                .fecha(entity.getFecha())
                .hora(entity.getHora())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado())
                .nombreCliente(isAdmin && usuario != null ? usuario.getNombre() : null)
                .correo(isAdmin && usuario != null ? usuario.getCorreo() : null)
                .telefono(isAdmin && usuario != null ? usuario.getTelefono() : null)
                .build();
    }
}