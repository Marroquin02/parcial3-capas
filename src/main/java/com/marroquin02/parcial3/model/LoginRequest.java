package com.marroquin02.parcial3.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}