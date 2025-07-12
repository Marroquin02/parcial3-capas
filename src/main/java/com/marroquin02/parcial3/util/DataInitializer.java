package com.marroquin02.parcial3.util;

import com.marroquin02.parcial3.model.Rol;
import com.marroquin02.parcial3.model.Usuario;
import com.marroquin02.parcial3.service.RolService;
import com.marroquin02.parcial3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private RolService rolService;
    private UsuarioService usuarioService;


    DataInitializer(RolService rolService, UsuarioService usuarioService) {
        this.rolService = rolService;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<Rol> rolAdmin = rolService.findByNombre("ADMIN");
        if (rolAdmin.isEmpty()) {
            Rol nuevoRolAdmin = rolService.crearRol("ADMIN", "Rol de ADMIN del sistema");
            System.out.println("Rol ADMIN creado: " + nuevoRolAdmin.getNombre());
        } else {
            System.out.println("Rol ADMIN ya existe");
        }

        if (!usuarioService.existsByUsername("admin")) {
            Usuario adminUser = usuarioService.crearUsuario("admin", "admin", "ADMIN");
            System.out.println("Usuario ADMIN creado: " + adminUser.getUsername());
        } else {
            System.out.println("Usuario ADMIN ya existe");
        }

        Optional<Rol> rolUsuario = rolService.findByNombre("USER");
        if (rolUsuario.isEmpty()) {
            Rol nuevoRolUsuario = rolService.crearRol("USER", "Rol de usuario estándar del sistema");
            System.out.println("Rol USUARIO creado: " + nuevoRolUsuario.getNombre());
        } else {
            System.out.println("Rol USUARIO ya existe");
        }
    }
}