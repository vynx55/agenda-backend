// CitaServiceImpl.java
package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import com.proyecto.mapper.CitaMapper;
import com.proyecto.repository.CitaRepository;
import com.proyecto.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    private Usuario getUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) auth.getPrincipal();
    }

    @Override
    public List<CitaResponseDTO> listar() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaResponseDTO> buscar(Long id) {
        return citaRepository.findById(id)
                .map(citaMapper::toResponse);
    }

    @Override
    public CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username) {
        Usuario usuario = getUsuarioAutenticado();
        Cita cita = citaMapper.toEntity(requestDTO);
        cita.setUsuario(usuario);
        return citaMapper.toResponse(citaRepository.save(cita));
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

        return citaMapper.toResponse(citaRepository.save(cita));
    }

    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<CitaResponseDTO> listarPorUsername(String username) {
        Usuario usuario = getUsuarioAutenticado();
        return citaRepository.findByUsuario(usuario).stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarCitaPorUsuario(Long id, String username) {
        Usuario usuario = getUsuarioAutenticado();
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!cita.getUsuario().getUsername().equals(usuario.getUsername())) {
            throw new RuntimeException("No puedes cancelar una cita que no te pertenece.");
        }

        cita.setEstado("CANCELADA");
        citaRepository.save(cita);
    }
}
