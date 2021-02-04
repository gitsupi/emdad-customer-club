package com.salinteam.emdadcustomerclub.controller;

import com.salinteam.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salinteam.emdadcustomerclub.model.*;
import com.salinteam.emdadcustomerclub.payload.*;
import com.salinteam.emdadcustomerclub.repository.*;
import com.salinteam.emdadcustomerclub.security.CurrentCompany;
import com.salinteam.emdadcustomerclub.security.JwtTokenProvider;
import com.salinteam.emdadcustomerclub.security.CompanyPrincipal;
import com.salinteam.emdadcustomerclub.service.ScoreService;
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
    GroupLevelRepository groupLevelRepository;

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
    public CompanyPrincipal getCurrentUser(@CurrentCompany CompanyPrincipal currentUser) {
        System.out.println(currentUser);
        return currentUser;
    }

    @PostMapping("/user/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewuser(@Valid @RequestBody AddNewUserRequest addNewUserRequest,
                                        @CurrentCompany CompanyPrincipal currentUser) {

        Optional<User> useropt = userRepository.findByPhonenumber(addNewUserRequest.getPhonenumber());

        String email = addNewUserRequest.getEmail();
        if (email != null) {
            if (!email.isEmpty()) {
                Optional<User> emailopt = userRepository.findByEmail(email);
                if (emailopt.isPresent()) {
                    return new ResponseEntity(new ApiResponse(true, "Email is already taken!"),
                            HttpStatus.OK);
                }
            }
        }

        String username = addNewUserRequest.getUsername();
        if (username != null)
            if (!username.isEmpty())
                if (userRepository.existsByUsername(username)) {
                    return new ResponseEntity(new ApiResponse(true, "Username is already taken!"),
                            HttpStatus.OK);
                }


//        Long grouplevel_id = addNewUserRequest.getGrouplevel_id();
        long id = 1L;
        GroupLevel groupLevel = groupLevelRepository
                .findGroupLevelById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupLevel", "grouplevel_id", id));


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
            return new ResponseEntity(new ApiResponse(true, "User registered successfully"),
                    HttpStatus.OK);
        }
        User user = new User(addNewUserRequest.getFirstname(),
                addNewUserRequest.getLastname(),
                username,
                addNewUserRequest.getPhonenumber(),
                email,
                groupLevel.getMinscore().intValue());

        user.setGroupLevel(groupLevel);
        user.setCompanies(Collections.singleton(new Company(currentUser.getId())));


        User result = userRepository.save(user);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User registered successfully"));
    }


    @PostMapping("/event/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewevent(@Valid @RequestBody CoEvent coEvent,
                                         @CurrentCompany CompanyPrincipal currentUser) {

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
                                      @CurrentCompany CompanyPrincipal currentUser) {



        String userId = eventUsingRequest.getUserId();

        //todo update this way
        User user = userRepository.findByPhonenumber(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", userId));     String eventId = eventUsingRequest.getEventId();

        CoEvent coEvent = coEventRepository.findByEventIdAndCompanyAndGroupLevel(eventId,
                new Company(currentUser.getId()), user.getGroupLevel())
                .orElseThrow(() -> new ResourceNotFoundException("event", "username", eventId));



        user.getCompanies().forEach(company -> {
            System.out.println(company.getId());
            System.out.println(company.getUsername());
            System.out.println(company.getName());
        });


        Integer beforescore = user.getScore();
        user.setScore(coEvent.getScoreValue() + beforescore);
        GroupLevel groupLevel1 = groupLevelRepository.findByScore(user.getScore().longValue())
                .orElseThrow(() -> new ResourceNotFoundException("GroupLevel",
                        "score", eventId));


        user.setGroupLevel(groupLevel1);
        userRepository.save(user);

        //logging  system events used by users of companies
        userEventLogRepository.save(new UserEventLog(coEvent,
                user, coEvent.getScoreValue()));

        return ResponseEntity.ok().body(new ApiResponse(true, "score added"));

    }


    @PostMapping("/transaction/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewTrans(@Valid @RequestBody CoTransaction coTransaction,
                                         @CurrentCompany CompanyPrincipal currentUser) {

        try {
            Company company = new Company();
            company.setId(currentUser.getId());
            coTransactionRepository.save(coTransaction.setCompany(company));
            return ResponseEntity.ok(new ApiResponse(true,
                    "transaction added successfully"));

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "transactionId duplicated"));

        }
    }


    @PostMapping("/transaction/use")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> useTrans(@Valid @RequestBody TransactionUsingRequest transactionUsingRequest,
                                      @CurrentCompany CompanyPrincipal currentUser) {

        String userId = transactionUsingRequest.getUserId();
        //todo update this way
        User user = userRepository.findByPhonenumber(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", userId));

        GroupLevel groupLevel = user.getGroupLevel();


        String transId = transactionUsingRequest.getTransId();

        CoTransaction coTransaction = coTransactionRepository.findByTransactionIdAndCompanyAndGroupLevel(transId,
                new Company(currentUser.getId()), groupLevel)
                .orElseThrow(() -> new ResourceNotFoundException("The User Could Not Operate At This Level. ", "transId", transId  ));



//        GroupLevel groupLevel = groupLevelRepository.findGroupLevelById(userLevelId)
//                .orElseThrow(() -> new ResourceNotFoundException("groupLevel", "groupLevel", userLevelId));




        Long price = transactionUsingRequest.getPrice();

        long scorable = price / coTransaction.getUnitprice();

        Integer beforescore = user.getScore();

        user.setScore((int) (scorable + beforescore));

        GroupLevel groupLevel1 = groupLevelRepository.findByScore(user.getScore().longValue())
                .orElseThrow(() -> new ResourceNotFoundException("GroupLevel", "score", transId));


        user.setGroupLevel(groupLevel1);

        userRepository.save(user);

        //logging  transactions used by users of companies
        userTransactionLogRepository.save(new UserTransactionLog(coTransaction,
                user, ((int) scorable), price));

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "score added"));

    }


    @Autowired
    ScoreService scoreService;


    @GetMapping("/user/{phonenumber}/score")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> userScore(@PathVariable String phonenumber,
                                      @CurrentCompany CompanyPrincipal companyPrincipal) {

        long userScore = scoreService.getUserScore(phonenumber, companyPrincipal);

        RestResponse<ScoreResponse> scoreResponseRestResponse = new RestResponse<>();
        scoreResponseRestResponse.setResult(new ScoreResponse(userScore, scoreService.getscoreval(), phonenumber));

        return ResponseEntity.ok()
                .body(scoreResponseRestResponse);

    }


    @PostMapping("/user/{phonenumber}/usescore")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public RestResponse<?> useScore(@PathVariable String phonenumber,
                                    @Valid @RequestBody ScoreUsing scoreUsing,
                                      @CurrentCompany CompanyPrincipal companyPrincipal) {

        long userScore = scoreService.getUserScore(phonenumber, companyPrincipal);



        RestResponse<ScoreResponse> scoreResponseRestResponse = new RestResponse<>();
        scoreResponseRestResponse.setResult(new ScoreResponse(userScore, scoreService.getscoreval(), phonenumber));

        return scoreResponseRestResponse;

    }





}