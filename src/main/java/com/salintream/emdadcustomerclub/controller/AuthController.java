package com.salintream.emdadcustomerclub.controller;

import com.salintream.emdadcustomerclub.model.Business;
import com.salintream.emdadcustomerclub.payload.ApiResponse;
import com.salintream.emdadcustomerclub.payload.JwtAuthenticationResponse;
import com.salintream.emdadcustomerclub.payload.LoginRequest;
import com.salintream.emdadcustomerclub.payload.SignUpRequest;
import com.salintream.emdadcustomerclub.repository.BusinessRepository;
import com.salintream.emdadcustomerclub.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@Api(value = "/api/auth", description = "Customer Profile", produces = "application/json")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BusinessRepository businessRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (businessRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }


        // Creating user's account
        Business user = new Business(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Business result = businessRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/businesses/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }


}