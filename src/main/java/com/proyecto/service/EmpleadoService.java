package com.proyecto.service;

import com.proyecto.dto.Empleado.EmpleadoRequest;
import com.proyecto.dto.Empleado.EmpleadoResponse;
import com.proyecto.entity.Empleado;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> listar();
    EmpleadoResponse crear(EmpleadoRequest req);
    EmpleadoResponse actualziar(Long id, EmpleadoRequest req);
    void eliminar(Long id);
    Empleado obtenerPorId(Long id);
}
