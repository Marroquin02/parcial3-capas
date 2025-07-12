package com.marroquin02.parcial3.service;

import com.marroquin02.parcial3.model.Rol;
import com.marroquin02.parcial3.model.Usuario;
import com.marroquin02.parcial3.repository.RolRepository;
import com.marroquin02.parcial3.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;

    UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public Usuario crearUsuario(String username, String password, String nombreRol) {
        Optional<Rol> rol = rolRepository.findByNombre(nombreRol);
        if (rol.isPresent()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setRol(rol.get());
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Rol no encontrado: " + nombreRol);
    }

    public boolean validarCredenciales(String username, String password) {
        Optional<Usuario> usuario = findByUsername(username);
        log.info("Usuario encontrado: {}.\nUsuario: {} y contraseña: {}", usuario.isPresent(), usuario.get().getUsername(), usuario.get().getPassword());
        return usuario.isPresent() && usuario.get().getPassword().equals(password);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) {
        Optional<Usuario> usuario = findByUsername(username);
        if (usuario.isPresent()) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.get().getUsername())
                    .password(usuario.get().getPassword())
                    .roles(usuario.get().getRol().getNombre())
                    .build();
        }
        throw new RuntimeException("Usuario no encontrado: " + username);
    }
}