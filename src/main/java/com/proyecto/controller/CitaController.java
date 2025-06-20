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

    // 🟥 ADMIN: Ver todas las citas
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CitaResponseDTO> listar() {
        return service.listar();
    }

    // 🟨 USER: Ver sus propias citas
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mis-citas")
    public List<CitaResponseDTO> listarMisCitas() {
        return service.listarPorUsername(null); // ya no se usa el username internamente
    }

    // 🟨 USER y ADMIN: Crear una cita
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public CitaResponseDTO crear(@RequestBody CitaRequestDTO dto) {
        return service.guardar(dto, null); // ya no se usa el username
    }

    // 🟥 ADMIN: Editar cita
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CitaResponseDTO editar(@PathVariable Long id, @RequestBody CitaRequestDTO dto) {
        return service.editar(id, dto);
    }

    // 🟥 ADMIN: Eliminar cita
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    // 🟨 USER y ADMIN: Cancelar cita propia
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/cancelar/{id}")
    public void cancelarCitaPropia(@PathVariable Long id) {
        service.cancelarCitaPorUsuario(id, null); // username se ignora
    }
}
