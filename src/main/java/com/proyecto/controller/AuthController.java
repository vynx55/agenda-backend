package com.proyecto.controller;

import com.proyecto.JWT.JwtUtil;
import com.proyecto.entity.Usuario;
import com.proyecto.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword())
        );
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.registrar(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping("/crear-admin")
    public ResponseEntity<Usuario> crearAdmin(@RequestBody Usuario usuario) {
        Usuario admin = usuarioService.crearAdmin(usuario);
        return ResponseEntity.ok(admin);
    }
}
