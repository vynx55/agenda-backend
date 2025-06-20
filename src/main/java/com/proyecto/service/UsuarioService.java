package com.proyecto.service;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepositorio usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.USUARIO);
        return usuarioRepository.save(usuario);
    }

    public Usuario crearAdmin(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.ADMIN);
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }
}
