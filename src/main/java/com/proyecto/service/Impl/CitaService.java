package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CitaService {

    List<CitaResponseDTO> listar();
    Optional<CitaResponseDTO> buscar(Long id);

    // ✅ Recibe el username para guardar (lo pasamos desde el controller usando Authentication)
    CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username);

    CitaResponseDTO editar(Long id, CitaRequestDTO requestDTO);
    void eliminar(Long id);

    // ✅ Ver citas del usuario autenticado
    List<CitaResponseDTO> listarPorUsername(String username);
}
