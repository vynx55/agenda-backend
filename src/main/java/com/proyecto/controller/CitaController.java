package com.proyecto.controller;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.service.Impl.CitaService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CitaController {

    private final CitaService service;



    // 🟩 ADMIN: Ver todas las citas
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CitaResponseDTO> listar() {
        return service.listar();
    }

    // 🟨 USER: Ver solo sus propias citas
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/mis-citas")
    public List<CitaResponseDTO> listarMisCitas(Principal principal) {
        return service.listarPorUsername(principal.getName());
    }

    // 🟨 USER y ADMIN pueden crear
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public CitaResponseDTO crear(@RequestBody CitaRequestDTO dto, Principal principal) {
        return service.guardar(dto, principal.getName());
    }

    // 🟥 Solo ADMIN puede editar
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CitaResponseDTO editar(@PathVariable Long id, @RequestBody CitaRequestDTO dto) {
        return service.editar(id, dto);
    }

    // 🟥 Solo ADMIN puede eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}