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
    @NotEmpty (message = "email no puede estar vacio")
    @Email (message = "Formato del email invalido")
    private String email;
    @NotEmpty (message = "Contraseña no puede estar vacia")
    private String password;
}
