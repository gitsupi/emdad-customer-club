package com.salintream.emdadcustomerclub.controller;

import com.salintream.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salintream.emdadcustomerclub.model.*;
import com.salintream.emdadcustomerclub.payload.AddNewUserRequest;
import com.salintream.emdadcustomerclub.payload.ApiResponse;
import com.salintream.emdadcustomerclub.payload.EventUsingRequest;
import com.salintream.emdadcustomerclub.payload.TransactionUsingRequest;
import com.salintream.emdadcustomerclub.repository.*;
import com.salintream.emdadcustomerclub.security.CurrentUser;
import com.salintream.emdadcustomerclub.security.JwtTokenProvider;
import com.salintream.emdadcustomerclub.security.UserPrincipal;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

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
    CoEventRepository coEventRepository;

    @Autowired
    CoTransactionRepository coTransactionRepository;

    @Autowired
    UserEventLogRepository userEventLogRepository;

    @Autowired
    UserTransactionLogRepository userTransactionLogRepository;

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

        if (useropt.isPresent()) {
            Set<Company> companies = useropt.get().getCompanies();
            for (Company company : companies) {
                if (company.getId() == currentUser.getId()) {
                    return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                            HttpStatus.OK);
                }
            }
            companies.add(new Company(currentUser.getId()));
            userRepository.save(useropt.get());
            return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                    HttpStatus.OK);
        }


        User user = new User(addNewUserRequest.getFirstname(),
                addNewUserRequest.getLastname(),
                addNewUserRequest.getUsername(),
                addNewUserRequest.getPhonenumber(),
                0);

        user.setCompanies(Collections.singleton(new Company(currentUser.getId())));

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




    @PostMapping("/transaction/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewTrans(@Valid @RequestBody CoTransaction coTransaction,
                                         @CurrentUser UserPrincipal currentUser) {

        try {
            Company company = new Company();
            company.setId(currentUser.getId());
            coTransactionRepository.save(coTransaction.setCompany(company));
            return ResponseEntity.ok(new ApiResponse(true,
                    "transaction added successfully"));

        }catch (DataIntegrityViolationException  e)
        {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "username duplicated"));

        }
    }




    @PostMapping("/transaction/use")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> useTrans(@Valid @RequestBody TransactionUsingRequest transactionUsingRequest,
                                      @CurrentUser UserPrincipal currentUser) {


        String transId = transactionUsingRequest.getTransId();
        CoTransaction coTransaction = coTransactionRepository.findByUsernameAndCompany(transId,
                new Company(currentUser.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "transId", transId));


        String userId = transactionUsingRequest.getUserId();

        //todo update this way
        User user = userRepository.findByPhonenumber(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", userId));


        user.getCompanies().forEach(company -> {
            System.out.println(company.getId());
            System.out.println(company.getUsername());
            System.out.println(company.getName());
        });

        Long price = transactionUsingRequest.getPrice();

        long scorable = price / coTransaction.getUnitprice();

        Integer beforescore = user.getScore();

        user.setScore((int) (scorable + beforescore));

        userRepository.save(user);

        //logging  transactions used by users of companies
        userTransactionLogRepository.save(new UserTransactionLog(coTransaction.getId(),
                user, ((int) scorable), price));

        return ResponseEntity.ok().body(new ApiResponse(true, "score added"));

    }


}