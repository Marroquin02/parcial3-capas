package com.marroquin02.parcial3.controller;

import com.marroquin02.parcial3.model.LoginRequest;
import com.marroquin02.parcial3.model.LoginResponse;
import com.marroquin02.parcial3.model.RegisterRequest;
import com.marroquin02.parcial3.model.Usuario;
import com.marroquin02.parcial3.service.UsuarioService;
import com.marroquin02.parcial3.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class AuthController {

    private UsuarioService usuarioService;
    private JwtUtil jwtUtil;

    AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (usuarioService.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("El usuario ya existe");
            }

            Usuario nuevoUsuario = usuarioService.crearUsuario(
                    registerRequest.getUsername(),
                    registerRequest.getPassword(),
                    "USER"
            );

            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            log.info("Login request: {}", loginRequest);
            if (usuarioService.validarCredenciales(loginRequest.getUsername(), loginRequest.getPassword())) {
                Optional<Usuario> usuario = usuarioService.findByUsername(loginRequest.getUsername());

                if (usuario.isPresent()) {
                    String token = jwtUtil.generateToken(loginRequest.getUsername());
                    LoginResponse response = new LoginResponse(
                            token,
                            usuario.get().getUsername(),
                            usuario.get().getRol().getNombre()
                    );
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.badRequest().body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el login: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<Usuario> usuario = usuarioService.findByUsername(username);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            }
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error obteniendo perfil: " + e.getMessage());
        }
    }
}