package com.proyecto.controller;

import com.proyecto.JWT.JwtUtil;
import com.proyecto.entity.Rol;
import com.proyecto.dto.Auth.LoginRequest;
import com.proyecto.dto.Auth.AuthResponse;
import com.proyecto.entity.Usuario;
import com.proyecto.service.Impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario req) {
        req.setRol(Rol.USUARIO);
        Usuario registrado = usuarioService.registrar(req);
        return ResponseEntity.ok(registrado);
    }

    @PostMapping("/crear-admin")
    public ResponseEntity<?> crearAdmin(@RequestBody Usuario req) {
        req.setRol(Rol.ADMIN);
        Usuario admin = usuarioService.registrar(req);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        UserDetails userDetails = usuarioService.loadUserByUsername(req.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
