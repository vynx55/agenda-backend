package com.proyecto.controller;

import com.proyecto.dto.Cita.CitaRequestDTO;
import com.proyecto.dto.Cita.CitaResponseDTO;
import com.proyecto.service.Impl.CitaService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CitaController {

    private final CitaService service;


    @GetMapping
    public List<CitaResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public Optional<CitaResponseDTO> buscar(@PathVariable Long id){
        return service.buscar(id);
    }

    @PostMapping
    public CitaResponseDTO crear(@RequestBody CitaRequestDTO dto){
        return service.guardar(dto);
    }

    @PutMapping("/{id}")
    public CitaResponseDTO editar(@PathVariable Long id, @RequestBody CitaRequestDTO dto){
        return service.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
