package edu.progra3.polarcity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class SignupDTO {
    @NotEmpty(message = "El nombre de usuario no puede esta vacío o ser nulo.")
    private String username;

    @NotEmpty(message = "El email no puede esta vacío o ser nulo.")
    @Email (message = "El formato del email inválido.")
    private String email;

    @NotEmpty(message = "La contraseña no puede esta vacía o ser nula.")
    private String password;
}
