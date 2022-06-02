package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.UserDTO;
import edu.progra3.polarcity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/username")
    public ResponseEntity<UserDTO> findByUsername(@RequestParam(name = "search") String username){
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam(name = "search") String email){
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }
}
