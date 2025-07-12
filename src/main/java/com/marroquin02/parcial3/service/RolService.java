package com.marroquin02.parcial3.service;

import com.marroquin02.parcial3.model.Rol;
import com.marroquin02.parcial3.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    private RolRepository rolRepository;

    RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    public Rol crearRol(String nombre, String descripcion) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        rol.setDescripcion(descripcion);
        return rolRepository.save(rol);
    }
}