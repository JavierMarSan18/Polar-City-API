package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    UserDTO findByEmail(String email);
}
