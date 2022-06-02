package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.JwtAuthResponseDTO;
import edu.progra3.polarcity.dto.LoginDTO;
import edu.progra3.polarcity.dto.SignupDTO;
import edu.progra3.polarcity.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.authenticate(loginDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDTO signupDTO){
        return ResponseEntity.ok(authenticationService.signup(signupDTO));
    }
}
