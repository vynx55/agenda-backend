package com.proyecto.service;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    String exportarCitasACSV();
    Page<CitaResponseDTO> listarPaginadoFiltrado(String estado, LocalDate fecha, Pageable pageable);
    List<CitaResponseDTO> listar();
    Optional<CitaResponseDTO> buscar(Long id);
    CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username);
    CitaResponseDTO editar(Long id, CitaRequestDTO requestDTO);
    void eliminar(Long id);
    List<CitaResponseDTO> listarPorUsername(String username);
    void cancelarCitaPorUsuario(Long id, String username);
    void actualizarEstado(Long id, EstadoCita nuevoEstado);
}
