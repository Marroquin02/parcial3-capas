package com.marroquin02.parcial3.controller;

import com.marroquin02.parcial3.model.Usuario;
import com.marroquin02.parcial3.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AdminController {

    private UsuarioService usuarioService;

    AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error obteniendo usuarios: " + e.getMessage());
        }
    }
}