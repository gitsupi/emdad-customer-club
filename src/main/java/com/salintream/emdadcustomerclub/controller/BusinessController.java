package com.salintream.emdadcustomerclub.controller;

import com.salintream.emdadcustomerclub.model.Business;
import com.salintream.emdadcustomerclub.model.User;
import com.salintream.emdadcustomerclub.payload.AddNewUserRequest;
import com.salintream.emdadcustomerclub.payload.ApiResponse;
import com.salintream.emdadcustomerclub.repository.BusinessRepository;
import com.salintream.emdadcustomerclub.repository.UserRepository;
import com.salintream.emdadcustomerclub.security.CurrentUser;
import com.salintream.emdadcustomerclub.security.JwtTokenProvider;
import com.salintream.emdadcustomerclub.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

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

        Optional<User> useropt = userRepository.findByPhonenumber(addNewUserRequest.getPhonenumber());

        if (useropt.isPresent()) {
            useropt.get().getBusinesses().add(new Business(currentUser.getId()));
            userRepository.save(useropt.get());
            return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                    HttpStatus.OK);
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