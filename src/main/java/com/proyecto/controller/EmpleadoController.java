package com.proyecto.controller;

import com.proyecto.dto.Empleado.EmpleadoRequest;
import com.proyecto.dto.Empleado.EmpleadoResponse;
import com.proyecto.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@PreAuthorize("hasRole('ADMIN')")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    public List<EmpleadoResponse> listar(){
        return service.listar();
    }

    @PostMapping
    public EmpleadoResponse crear(@RequestBody EmpleadoRequest req){
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public EmpleadoResponse actualizar(@PathVariable Long id, @RequestBody EmpleadoRequest req){
        return service.actualziar(id,req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        service.eliminar(id);
    }
}
