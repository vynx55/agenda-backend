package com.proyecto.controller;

import com.proyecto.JWT.JwtUtil;
import com.proyecto.dto.Auth.AuthResponse;
import com.proyecto.dto.Auth.LoginRequest;
import com.proyecto.entity.Rol;
import com.proyecto.entity.Usuario;
import com.proyecto.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final UsuarioService usuarioService;
    private final com.proyecto.service.Impl.UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario req) {
        req.setRol(Rol.USUARIO);
        return ResponseEntity.ok(usuarioService.registrar(req));
    }

    @PostMapping("/crear-admin")
    public ResponseEntity<Usuario> crearAdmin(@RequestBody Usuario req) {
        req.setRol(Rol.ADMIN);
        return ResponseEntity.ok(usuarioService.crearAdmin(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getUsername(), req.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
