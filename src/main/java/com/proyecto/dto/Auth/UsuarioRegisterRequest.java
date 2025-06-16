package com.proyecto.dto.Auth;

import lombok.Data;

@Data
public class UsuarioRegisterRequest {
    private String username;
    private String password;
    private String nombre;
    private String correo;
    private String telefono;
}
