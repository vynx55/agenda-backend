package com.proyecto.controller;

import com.proyecto.JWT.JwtUtil;
import com.proyecto.dto.Auth.LoginRequest;
import com.proyecto.dto.Auth.AuthResponse;
import com.proyecto.entity.Usuario;
import com.proyecto.service.Impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails user = usuarioService.loadUserByUsername(req.getUsername());
        Usuario entity = usuarioService.buscarPorUsername(req.getUsername());
        String token = jwtUtil.generateToken(user, entity.getRol());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario u) {
        return usuarioService.registrar(u);
    }
}
