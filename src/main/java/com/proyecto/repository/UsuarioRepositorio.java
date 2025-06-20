package com.proyecto.repository;

import com.proyecto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
