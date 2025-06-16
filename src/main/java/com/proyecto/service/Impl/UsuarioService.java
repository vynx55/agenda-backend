package com.proyecto.service.Impl;

import com.proyecto.entity.Usuario;
import com.proyecto.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepositorio repo;
    private PasswordEncoder encoder;

    public UsuarioService(UsuarioRepositorio repo) {
        this.repo = repo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRol())
                .build();
    }

    public Usuario registrar(Usuario u) {
        u.setPassword(encoder.encode(u.getPassword()));
        u.setRol("USER");
        return repo.save(u);
    }

    public Usuario buscarPorUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
