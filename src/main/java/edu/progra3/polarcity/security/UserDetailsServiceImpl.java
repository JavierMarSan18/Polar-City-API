package edu.progra3.polarcity.security;

import edu.progra3.polarcity.entities.Role;
import edu.progra3.polarcity.entities.User;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuario no econtrado"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRole(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRole(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
