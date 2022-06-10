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
    @NotEmpty(message = "El email no puede esta vacío o ser nulo.")
    @Email (message = "El formato del email inválido.")
    private String email;

    @NotEmpty(message = "La contraseña no puede esta vacío o ser nula.")
    private String password;
}
