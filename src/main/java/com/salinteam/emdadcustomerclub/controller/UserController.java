package com.salinteam.emdadcustomerclub.controller;

import com.google.gson.Gson;
import com.salinteam.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salinteam.emdadcustomerclub.model.User;
import com.salinteam.emdadcustomerclub.model.UserEventLog;
import com.salinteam.emdadcustomerclub.model.UserTransactionLog;
import com.salinteam.emdadcustomerclub.payload.UserInfoResponse;
import com.salinteam.emdadcustomerclub.repository.UserEventLogRepository;
import com.salinteam.emdadcustomerclub.repository.UserRepository;
import com.salinteam.emdadcustomerclub.repository.UserTransactionLogRepository;
import com.salinteam.emdadcustomerclub.security.CurrentUser;
import com.salinteam.emdadcustomerclub.security.UserPrincipal;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 1/31/2021 , 9:14 PM.
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserEventLogRepository userEventLogRepository;

    @Autowired
    UserTransactionLogRepository userTransactionLogRepository;


    @PostMapping("/user/info/{phonenumber}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> addnewuser(@PathVariable String phonenumber,
                                        @CurrentUser UserPrincipal currentUser) {

        //todo update this way
        User user = userRepository.findByPhonenumber(phonenumber)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", phonenumber));

        return ResponseEntity.ok(new UserInfoResponse(phonenumber, user.getGroupLevel(), user.getScore()));

    }

    @GetMapping("/user/eventlogs/{phonenumber}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?> getUserEventLog(@PathVariable String phonenumber) {
        List<UserEventLog> userEventLogsByUser_phonenumber = userEventLogRepository.findUserEventLogsByUser_Phonenumber(phonenumber);
        return ResponseEntity.ok(userEventLogsByUser_phonenumber);

    }


    @GetMapping("/user/transactionlogs/{phonenumber}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    public ResponseEntity<?>  getUserTransactionLog(@PathVariable String phonenumber) {
        List<UserTransactionLog> userTransactionLogsByUserPhonenumber = userTransactionLogRepository.findUserTransactionLogsByUser_Phonenumber(phonenumber);
        return ResponseEntity.ok(userTransactionLogsByUserPhonenumber);

    }





}
