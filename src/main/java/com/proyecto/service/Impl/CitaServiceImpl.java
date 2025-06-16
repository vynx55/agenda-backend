package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import com.proyecto.mapper.CitaMapper;
import com.proyecto.repository.CitaRepository;
import lombok.AllArgsConstructor;
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

    @Override
    public List<CitaResponseDTO> listar() {
        return citaRepository.findAll().stream()
                .map(citaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CitaResponseDTO> buscar(Long id) {
        return citaRepository.findById(id).map(citaMapper::toResponse);
    }

    @Override
    public CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username) {
        Cita cita = citaMapper.toEntity(requestDTO);
        cita.setUsuario(usuarioService.buscarPorUsername(username)); // Aquí el usuario logueado
        return citaMapper.toResponse(citaRepository.save(cita));
    }


    @Override
    public CitaResponseDTO editar(Long id, CitaRequestDTO requestDTO) {
        Cita cita = citaMapper.toEntity(requestDTO);
        cita.setId(id);
        // Si quieres dejar el usuario fijo o no permitir editarlo, puedes omitirlo aquí
        return citaMapper.toResponse(citaRepository.save(cita));
    }


    @Override
    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    @Override
    public List<CitaResponseDTO> listarPorUsername(String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        List<Cita> citas = citaRepository.findByUsuario(usuario);
        return citas.stream()
                .map(citaMapper::toResponse)
                .toList();
    }
}
