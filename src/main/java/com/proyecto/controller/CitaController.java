package com.proyecto.controller;

import com.proyecto.dto.Cita.ActualizarEstadoDTO;
import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    // Solo ADMIN puede ver todas las citas
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    // Solo ADMIN puede ver una cita específica por ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> buscar(@PathVariable Long id) {
        return citaService.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // USER o ADMIN pueden crear una cita
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CitaResponseDTO> guardar(@RequestBody CitaRequestDTO requestDTO, Authentication auth) {
        String username = auth.getName();
        return ResponseEntity.ok(citaService.guardar(requestDTO, username));
    }

    // USER o ADMIN pueden editar una cita
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> editar(@PathVariable Long id, @RequestBody CitaRequestDTO requestDTO) {
        return ResponseEntity.ok(citaService.editar(id, requestDTO));
    }

    // USER o ADMIN pueden eliminar una cita
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Solo USER puede ver sus propias citas
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mis-citas")
    public ResponseEntity<List<CitaResponseDTO>> listarPorUsuario(Authentication auth) {
        System.out.println("Auth principal: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities()); // <--- AQUI

        String username = auth.getName();
        return ResponseEntity.ok(citaService.listarPorUsername(username));
    }

    // Solo USER puede cancelar una cita
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        citaService.cancelarCitaPorUsuario(id, username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/citas/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody @Validated ActualizarEstadoDTO dto) {
        citaService.actualizarEstado(id, dto.getEstado());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exportar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportarCitas() {
        String csv = citaService.exportarCitasACSV();

        byte[] bytes = csv.getBytes();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=citas.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(bytes);
    }

    @GetMapping("/paginado-filtrado")
    public Page<CitaResponseDTO> listarPaginadoFiltrado(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable) {
        return citaService.listarPaginadoFiltrado(estado, fecha, pageable);
    }
}

