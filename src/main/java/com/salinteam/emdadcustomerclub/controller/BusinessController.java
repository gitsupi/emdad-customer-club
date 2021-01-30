package com.salinteam.emdadcustomerclub.controller;

import com.salinteam.emdadcustomerclub.model.*;
import com.salinteam.emdadcustomerclub.payload.EventUsingRequest;
import com.salinteam.emdadcustomerclub.repository.*;
import com.salinteam.emdadcustomerclub.security.CurrentUser;
import com.salinteam.emdadcustomerclub.security.JwtTokenProvider;
import com.salinteam.emdadcustomerclub.security.UserPrincipal;
import com.salinteam.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salinteam.emdadcustomerclub.payload.AddNewUserRequest;
import com.salinteam.emdadcustomerclub.payload.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business", description = "client(company) functionalities such as adding user , event , transactions and use them to score users", produces = "application/json")
public class BusinessController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupLevelRepository groupLevelRepository;

    @Autowired
    CoEventRepository coEventRepository;

    @Autowired
    UserEventLogRepository userEventLogRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping("/company/me")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public UserPrincipal getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser);
        return currentUser;
    }

    @PostMapping("/user/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewuser(@Valid @RequestBody AddNewUserRequest addNewUserRequest,
                                        @CurrentUser UserPrincipal currentUser) {

        Optional<User> useropt = userRepository.findByPhonenumber(addNewUserRequest.getPhonenumber());
        Optional<User> emailopt = userRepository.findByEmail(addNewUserRequest.getEmail());
        boolean usernameopt = userRepository.existsByUsername(addNewUserRequest.getUsername());
        Optional<GroupLevel> groupLevelOptional = groupLevelRepository.findGroupLevelById(addNewUserRequest.getGrouplevel_id());

        if (useropt.isPresent()) {
            Set<Company> companies = useropt.get().getCompanies();
            for (Company company : companies) {
                if (company.getId() == currentUser.getId()) {
                    return new ResponseEntity(new ApiResponse(true, "Phone number is already taken!"),
                            HttpStatus.OK);
                }
            }

            companies.add(new Company(currentUser.getId()));
            userRepository.save(useropt.get());
            return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                    HttpStatus.OK);
        }

        if (emailopt.isPresent()){
            return new ResponseEntity(new ApiResponse(true, "Email is already taken!"),
                    HttpStatus.OK);
        }

        if (usernameopt){
            return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                    HttpStatus.OK);
        }


        User user = new User(addNewUserRequest.getFirstname(),
                addNewUserRequest.getLastname(),
                addNewUserRequest.getUsername(),
                addNewUserRequest.getPhonenumber(),
                addNewUserRequest.getEmail(),
                groupLevelOptional.get().getScore().intValue());

        GroupLevel groupLevel = new GroupLevel();
        groupLevel.setId(addNewUserRequest.getGrouplevel_id());


        user.setCompanies(Collections.singleton(new Company(currentUser.getId())));
        user.setGroupLevel(groupLevel);


        User result = userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User registered successfully"));
    }


    @PostMapping("/event/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewevent(@Valid @RequestBody CoEvent coEvent,
                                         @CurrentUser UserPrincipal currentUser) {

        try {
            Company company = new Company();
            company.setId(currentUser.getId());
            coEventRepository.save(coEvent.setCompany(company));
            return ResponseEntity.ok(new ApiResponse(true, "event added"));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getLocalizedMessage()));

        }
    }

    @PostMapping("/event/use")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> useevent(@Valid @RequestBody EventUsingRequest eventUsingRequest,
                                      @CurrentUser UserPrincipal currentUser) {


        String eventId = eventUsingRequest.getEventId();
        CoEvent coEvent = coEventRepository.findByUsernameAndCompany(eventId,
                new Company(currentUser.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("event", "username", eventId));


        String userId = eventUsingRequest.getUserId();

        //todo update this way
        User user = userRepository.findByPhonenumber(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", userId));


        user.getCompanies().forEach(company -> {
            System.out.println(company.getId());
            System.out.println(company.getUsername());
            System.out.println(company.getName());
        });


        Integer beforescore = user.getScore();
        user.setScore(coEvent.getScoreValue() + beforescore);
        userRepository.save(user);

        //logging  system events used by users of companies
        userEventLogRepository.save(new UserEventLog(coEvent.getId(),
               user, coEvent.getScoreValue()));

        return ResponseEntity.ok().body(new ApiResponse(true, "score added"));

    }


    @GetMapping("/dynamictest")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> test(@CurrentUser UserPrincipal currentUser) {

        User byUsername = userRepository.findByPhonenumber("09351844321").get();
        return ResponseEntity.ok(byUsername.getEventLogs());


    }


    }