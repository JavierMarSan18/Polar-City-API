package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.JwtAuthResponseDTO;
import edu.progra3.polarcity.dto.LoginDTO;
import edu.progra3.polarcity.dto.SignupDTO;

public interface AuthenticationService {
    JwtAuthResponseDTO authenticate(LoginDTO loginDTO);
    String signup(SignupDTO signupDTO);
}
