package com.marroquin02.parcial3.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String nombreUsuario;
    private String rol;
}