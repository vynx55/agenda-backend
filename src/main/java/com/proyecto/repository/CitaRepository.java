package com.proyecto.repository;

import com.proyecto.entity.Cita;
import com.proyecto.entity.EstadoCita;
import com.proyecto.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByUsuario(Usuario usuario);
    Page<Cita> findByEstado(EstadoCita estado, Pageable pageable);
    Page<Cita> findByFecha(LocalDate fecha, Pageable pageable);
    Page<Cita> findByEstadoAndFecha(EstadoCita estado, LocalDate fecha, Pageable pageable);

}