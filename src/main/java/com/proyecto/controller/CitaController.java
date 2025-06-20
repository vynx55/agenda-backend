package com.proyecto.controller;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.service.CitaService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CitaController {

    private final CitaService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CitaResponseDTO> listar() {
        return service.listar();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mis-citas")
    public List<CitaResponseDTO> listarMisCitas() {
        return service.listarPorUsername(null);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public CitaResponseDTO crear(@RequestBody CitaRequestDTO dto) {
        return service.guardar(dto, null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CitaResponseDTO editar(@PathVariable Long id, @RequestBody CitaRequestDTO dto) {
        return service.editar(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/cancelar/{id}")
    public void cancelarCitaPropia(@PathVariable Long id) {
        service.cancelarCitaPorUsuario(id, null);
    }
}
