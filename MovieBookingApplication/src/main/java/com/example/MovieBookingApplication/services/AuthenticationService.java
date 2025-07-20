package com.example.MovieBookingApplication.services;

import com.example.MovieBookingApplication.dto.LoginRequestDTO;
import com.example.MovieBookingApplication.dto.LoginResponseDTO;
import com.example.MovieBookingApplication.dto.RegisterRequestDTO;
import com.example.MovieBookingApplication.entities.User;
import com.example.MovieBookingApplication.jwt.JwtService;
import com.example.MovieBookingApplication.repositories.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if(userRespository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        Set<String> roles = new HashSet<String>();
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRespository.save(user);
    }

    public User registerAdminfUser(RegisterRequestDTO registerRequestDTO) {
        if(userRespository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        Set<String> roles = new HashSet<String>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRespository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        User user = userRespository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                               .jwtToken(token)
                               .username(user.getUsername())
                               .roles(user.getRoles())
                               .build();
    }
}
