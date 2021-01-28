package com.salintream.emdadcustomerclub.controller;

import com.salintream.emdadcustomerclub.model.Business;
import com.salintream.emdadcustomerclub.model.User;
import com.salintream.emdadcustomerclub.payload.*;
import com.salintream.emdadcustomerclub.repository.BusinessRepository;
import com.salintream.emdadcustomerclub.repository.RoleRepository;
import com.salintream.emdadcustomerclub.repository.UserRepository;
import com.salintream.emdadcustomerclub.security.BusinessPrincipal;
import com.salintream.emdadcustomerclub.security.CurrentUser;
import com.salintream.emdadcustomerclub.security.JwtTokenProvider;
import com.salintream.emdadcustomerclub.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business", description = "Customer Profile", produces = "application/json")
public class BusinessController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping("/user/me")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public UserPrincipal getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser);

        return currentUser;
    }

    @PostMapping("/addnewuser")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewuser(@Valid @RequestBody AddNewUserRequest addNewUserRequest,
                                        @CurrentUser UserPrincipal currentUser) {

        if (userRepository.existsByUsername(addNewUserRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }


        User user = new User(addNewUserRequest.getFirstname(),
                addNewUserRequest.getLastname(),
                addNewUserRequest.getUsername(),
                addNewUserRequest.getPhonenumber());

        user.setBusinesses(Collections.singleton(new Business(currentUser.getId())));

        User result = userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User registered successfully"));
    }


}