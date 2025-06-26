package com.proyecto.service.Impl;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.entity.Cita;
import com.proyecto.entity.Empleado;
import com.proyecto.entity.EstadoCita;
import com.proyecto.entity.Usuario;
import com.proyecto.mapper.CitaMapper;
import com.proyecto.repository.CitaRepository;
import com.proyecto.repository.EmpleadoRepository;
import com.proyecto.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final EmpleadoRepository repository;
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
        return citaRepository.findById(id).map(citaMapper::toResponse);
    }

    @Override
    public CitaResponseDTO guardar(CitaRequestDTO requestDTO, String username) {
        Usuario usuario = getUsuarioAutenticado();

        Empleado empleado = repository.findById(requestDTO.getEmpleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Cita cita = citaMapper.toEntity(requestDTO, empleado);
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
        try {
            EstadoCita estado = EstadoCita.valueOf(requestDTO.getEstado().toUpperCase());
            cita.setEstado(estado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado de cita inválido: " + requestDTO.getEstado());
        }
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

        cita.setEstado(EstadoCita.CANCELADA); // ❗ Usa enum, no String
        citaRepository.save(cita);
    }

    @Override
    public void actualizarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado(nuevoEstado);
        citaRepository.save(cita);
    }
    @Override
    public String exportarCitasACSV() {
        List<Cita> citas = citaRepository.findAll();

        StringBuilder sb = new StringBuilder();

        // Cabecera CSV
        sb.append("ID,Usuario,Fecha,Hora,Estado,Servicio,Precio,Observaciones\n");

        for (Cita cita : citas) {
            sb.append(cita.getId()).append(",");
            sb.append(cita.getUsuario().getUsername()).append(",");
            sb.append(cita.getFecha()).append(",");
            sb.append(cita.getHora()).append(",");
            sb.append(cita.getEstado()).append(",");
            sb.append(cita.getServicio() != null ? cita.getServicio() : "").append(",");
            sb.append(cita.getPrecio() != null ? cita.getPrecio() : "").append(",");
            sb.append(cita.getObservaciones() != null ? cita.getObservaciones() : "").append("\n");
        }

        return sb.toString();
    }

    @Override
    public Page<CitaResponseDTO> listarPaginadoFiltrado(String estado, LocalDate fecha, Pageable pageable) {
        EstadoCita estadoEnum = null;

        if (estado != null && !estado.isEmpty()) {
            try {
                estadoEnum = EstadoCita.valueOf(estado.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado inválido: " + estado);
            }
        }

        if (estadoEnum != null && fecha != null) {
            return citaRepository.findByEstadoAndFecha(estadoEnum, fecha, pageable)
                    .map(citaMapper::toResponse);
        } else if (estadoEnum != null) {
            return citaRepository.findByEstado(estadoEnum, pageable)
                    .map(citaMapper::toResponse);
        } else if (fecha != null) {
            return citaRepository.findByFecha(fecha, pageable)
                    .map(citaMapper::toResponse);
        } else {
            return citaRepository.findAll(pageable)
                    .map(citaMapper::toResponse);
        }
    }


}
