package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import com.proyecto.mapper.CitaMapper;
import com.proyecto.repository.CitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;
    private final UsuarioService usuarioService;

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public List<CitaResponseDTO> listar() {
        boolean admin = isAdmin();
        return citaRepository.findAll().stream()
                .map(cita -> citaMapper.toResponse(cita, admin))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaResponseDTO> buscar(Long id) {
        boolean admin = isAdmin();
        return citaRepository.findById(id)
                .map(cita -> citaMapper.toResponse(cita, admin));
    }

    @Override
    public CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Cita cita = citaMapper.toEntity(requestDTO);
        cita.setUsuario(usuario);
        return citaMapper.toResponse(citaRepository.save(cita), isAdmin());
    }

    @Override
    public CitaResponseDTO editar(Long id, CitaRequestDTO requestDTO) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setFecha(requestDTO.getFecha());
        cita.setHora(requestDTO.getHora());
        cita.setServicio(requestDTO.getServicio());
        cita.setPrecio(requestDTO.getPrecio());
        cita.setObservaciones(requestDTO.getObservaciones());
        cita.setEstado(requestDTO.getEstado());

        return citaMapper.toResponse(citaRepository.save(cita), isAdmin());
    }

    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<CitaResponseDTO> listarPorUsername(String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return citaRepository.findByUsuario(usuario).stream()
                .map(cita -> citaMapper.toResponse(cita, false)) // no es admin
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarCitaPorUsuario(Long id, String username) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!cita.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No puedes cancelar una cita que no te pertenece.");
        }

        cita.setEstado("CANCELADA");
        citaRepository.save(cita);
    }
}
