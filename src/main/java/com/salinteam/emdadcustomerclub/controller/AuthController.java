package com.salinteam.emdadcustomerclub.controller;

import com.salinteam.emdadcustomerclub.exception.AppException;
import com.salinteam.emdadcustomerclub.model.Role;
import com.salinteam.emdadcustomerclub.model.RoleName;
import com.salinteam.emdadcustomerclub.repository.CompanyRepository;
import com.salinteam.emdadcustomerclub.repository.RoleRepository;
import com.salinteam.emdadcustomerclub.security.JwtTokenProvider;
import com.salinteam.emdadcustomerclub.model.Company;
import com.salinteam.emdadcustomerclub.payload.ApiResponse;
import com.salinteam.emdadcustomerclub.payload.JwtAuthenticationResponse;
import com.salinteam.emdadcustomerclub.payload.LoginRequest;
import com.salinteam.emdadcustomerclub.payload.SignUpRequest;
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
import java.util.Collections;

/**
 * UriComponentsBuilder with additional static factory methods to create links
 *
 * @author Abolfazl Ghahremani
 * @since 0
 */

@RestController
@RequestMapping("/api/auth")
@Api(value = "/api/auth", description = "Customer Profile", produces = "application/json")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateCompany(@Valid @RequestBody LoginRequest loginRequest) {

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
    public ResponseEntity<?> registerCompany(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (companyRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating company's account
        Company company = new Company(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getPassword());

        company.setPassword(passwordEncoder.encode(company.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_COMPANY)
                .orElseThrow(() -> new AppException("Company Role not set."));

        company.setRoles(Collections.singleton(userRole));

        Company result = companyRepository.save(company);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/businesses/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Company registered successfully"));
    }


}