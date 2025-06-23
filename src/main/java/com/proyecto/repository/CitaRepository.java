package com.proyecto.repository;

import com.proyecto.entity.Cita;
import com.proyecto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByUsuario(Usuario usuario);
}