package com.proyecto.service.Impl;

import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepositorio repo;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String rolConPrefijo = "ROLE_" + user.getRol().name();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(rolConPrefijo))
        );
    }

    public Usuario registrar(Usuario u) {
        if (u.getRol() == Rol.ADMIN) {
            throw new RuntimeException("No puedes registrar administradores desde esta función.");
        }
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public Usuario buscarPorUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
