package com.proyecto.controller;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> buscar(@PathVariable Long id) {
        return citaService.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> guardar(@RequestBody CitaRequestDTO requestDTO, Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(citaService.guardar(requestDTO, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> editar(@PathVariable Long id, @RequestBody CitaRequestDTO requestDTO) {
        return ResponseEntity.ok(citaService.editar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mis-citas")
    public ResponseEntity<List<CitaResponseDTO>> listarPorUsuario(Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(citaService.listarPorUsername(username));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        citaService.cancelarCitaPorUsuario(id, username);
        return ResponseEntity.noContent().build();
    }
}
