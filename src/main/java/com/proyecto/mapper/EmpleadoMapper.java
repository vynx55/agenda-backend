package com.proyecto.mapper;

import com.proyecto.dto.Empleado.EmpleadoRequest;
import com.proyecto.dto.Empleado.EmpleadoResponse;
import com.proyecto.entity.Empleado;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequest request){
        Empleado e = new Empleado();
        e.setNombre(request.getNombre());
        e.setEspecialidad(request.getEspecialidad());
        e.setFotoUrl(request.getFotoUrl());
        return e;
    }

    public static EmpleadoResponse toResponse(Empleado e){
    EmpleadoResponse r = new EmpleadoResponse();
    r.setId(e.getId());
    r.setNombre(e.getNombre());
    r.setEspecialidad(e.getEspecialidad());
    r.setFotoUrl(e.getFotoUrl());
    return r;
    }
}
