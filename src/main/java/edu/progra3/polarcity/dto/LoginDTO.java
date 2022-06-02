package edu.progra3.polarcity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LoginDTO {
    @NotEmpty (message = "El email no puede estar vacío")
    @Email (message = "El formato del email inválido.")
    private String email;
    @NotEmpty (message = "La contraseña no puede estar vacía.")
    private String password;
}
