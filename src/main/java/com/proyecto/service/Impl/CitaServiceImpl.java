package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import com.proyecto.mapper.CitaMapper;
import com.proyecto.repository.CitaRepository;
import lombok.AllArgsConstructor;
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

    // 🟥 ADMIN: Ver todas las citas
    @Override
    public List<CitaResponseDTO> listar() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🟨 USER/ADMIN: Buscar una cita por ID (validación según necesidad)
    @Override
    public Optional<CitaResponseDTO> buscar(Long id) {
        return citaRepository.findById(id).map(citaMapper::toResponse);
    }

    // 🟨 USER/ADMIN: Crear una nueva cita vinculada al usuariao autenticado
    @Override
    public CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Cita cita = citaMapper.toEntity(requestDTO);
        cita.setUsuario(usuario);
        return citaMapper.toResponse(citaRepository.save(cita));
    }

    // 🟥 ADMIN: Editar cita sin cambiar usuario
    @Override
    public CitaResponseDTO editar(Long id, CitaRequestDTO requestDTO) {
        Cita cita = citaRepository.findById(id).orElseThrow();

        cita.setFecha(requestDTO.getFecha());
        cita.setHora(requestDTO.getHora());
        cita.setServicio(requestDTO.getServicio());
        cita.setPrecio(requestDTO.getPrecio());
        cita.setObservaciones(requestDTO.getObservaciones());
        cita.setEstado(requestDTO.getEstado());

        return citaMapper.toResponse(citaRepository.save(cita));
    }

    // 🟥 ADMIN: Eliminar cita
    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    // 🟨 USER: Ver solo sus citas
    @Override
    public List<CitaResponseDTO> listarPorUsername(String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return citaRepository.findByUsuario(usuario).stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarCitaPorUsuario(Long id, String username) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!cita.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No puedes cancelar una cita que no te pertenece.");
        }

        citaRepository.deleteById(id);
    }
}
