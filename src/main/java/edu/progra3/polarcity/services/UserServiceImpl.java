package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.UserDTO;
import edu.progra3.polarcity.entities.User;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.UserRepository;
import edu.progra3.polarcity.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapUtil mapUtil;

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapUtil.mapDTO(user);
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapUtil.mapDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return mapUtil.mapDTO(user);
    }


}
