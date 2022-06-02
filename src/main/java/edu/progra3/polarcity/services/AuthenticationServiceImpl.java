package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.JwtAuthResponseDTO;
import edu.progra3.polarcity.dto.LoginDTO;
import edu.progra3.polarcity.dto.SignupDTO;
import edu.progra3.polarcity.entities.Role;
import edu.progra3.polarcity.entities.User;
import edu.progra3.polarcity.exceptions.ConflictException;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.RoleRepository;
import edu.progra3.polarcity.repositories.UserRepository;
import edu.progra3.polarcity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthResponseDTO authenticate(LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtTokenProvider.generateToken(auth);

        return new JwtAuthResponseDTO(token);
    }

    @Override
    public String signup(SignupDTO signupDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signupDTO.getEmail()))){
            throw new ConflictException("Ya existe un usuario con el email: "+signupDTO.getEmail());
        }
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signupDTO.getUsername()))){
            throw new ConflictException("Ya existe un usuario con el nombre de usuario: "+signupDTO.getUsername());
        }

        User user = new User();
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new NotFoundException("Rol no encontrado"));
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return "Usuario registrado exitosamente";
    }
}
