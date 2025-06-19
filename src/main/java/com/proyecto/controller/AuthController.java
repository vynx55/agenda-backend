package com.proyecto.controller;

import com.proyecto.JWT.JwtUtil;
import com.proyecto.dto.Auth.LoginRequest;
import com.proyecto.dto.Auth.AuthResponse;
import com.proyecto.dto.Auth.UsuarioRegisterRequest;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario req) {
        req.setRol("USER");
        return ResponseEntity.ok(usuarioService.registrar(req));
    }

    @PostMapping("/crear-admin")
    public ResponseEntity<?> crearAdmin(@RequestBody Usuario req) {
        req.setRol("ADMIN");
        return ResponseEntity.ok(usuarioService.registrar(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails userDetails = usuarioService.loadUserByUsername(req.getUsername());
        Usuario entity = usuarioService.buscarPorUsername(req.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
