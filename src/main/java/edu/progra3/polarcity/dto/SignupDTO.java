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
    @NotEmpty (message = "Nombre de usuario no puede estar vacio")
    private String username;
    @NotEmpty (message = "email no puede estar vacio")
    @Email (message = "Formato de email Invalido")
    private String email;
    @NotEmpty (message = "Contraseña no puede estar vacio")
    private String password;
}
