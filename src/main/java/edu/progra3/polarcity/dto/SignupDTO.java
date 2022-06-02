package edu.progra3.polarcity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SignupDTO {
    @NotEmpty (message = "El nombre de usuario no puede estar vacío.")
    private String username;
    @NotEmpty (message = "EL email no puede estar vacío.")
    @Email (message = "El formato del email es inválído.")
    private String email;
    @NotEmpty (message = "La contraseña no puede estar vacía.")
    private String password;
}
