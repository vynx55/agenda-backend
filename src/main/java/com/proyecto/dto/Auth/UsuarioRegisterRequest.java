package com.proyecto.dto.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegisterRequest {
    private String username;
    private String password;
}
