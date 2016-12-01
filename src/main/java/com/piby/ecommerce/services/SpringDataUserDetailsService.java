package com.piby.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.piby.ecommerce.entities.User;
import com.piby.ecommerce.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author marco
 */
@Component
public class SpringDataUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public SpringDataUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                Stream.of(user.getRoles())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }

}

