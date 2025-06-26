package com.proyecto.service.Impl;

import com.proyecto.dto.Empleado.EmpleadoRequest;
import com.proyecto.dto.Empleado.EmpleadoResponse;
import com.proyecto.entity.Empleado;
import com.proyecto.mapper.EmpleadoMapper;
import com.proyecto.repository.EmpleadoRepository;
import com.proyecto.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    @Override
    public List<EmpleadoResponse> listar() {
        return repo.findAll().stream().map(EmpleadoMapper::toResponse).toList();
    }

    @Override
    public EmpleadoResponse crear(EmpleadoRequest req) {
        Empleado emp = EmpleadoMapper.toEntity(req);
        return EmpleadoMapper.toResponse(repo.save(emp));
    }

    @Override
    public EmpleadoResponse actualziar(Long id, EmpleadoRequest req) {
        Empleado emp = repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
        emp.setNombre(req.getNombre());
        emp.setEspecialidad(req.getEspecialidad());
        emp.setFotoUrl(req.getFotoUrl());
        return EmpleadoMapper.toResponse(repo
                .save(emp));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Empleado obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Empleado no Encontrado"));
    }
}
