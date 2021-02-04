package com.salinteam.emdadcustomerclub.service;

import com.salinteam.emdadcustomerclub.exception.ObjectNotFoundException;
import com.salinteam.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salinteam.emdadcustomerclub.model.ScoreVal;
import com.salinteam.emdadcustomerclub.model.User;
import com.salinteam.emdadcustomerclub.repository.ScoreValRepository;
import com.salinteam.emdadcustomerclub.repository.UserRepository;
import com.salinteam.emdadcustomerclub.security.CompanyPrincipal;
import com.salinteam.emdadcustomerclub.security.CurrentCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 2/3/2021 , 3:21 PM.
 */

@Service
public class ScoreService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScoreValRepository scoreValRepository;


    public Long getUserScore(String phonenumber, CompanyPrincipal companyPrincipal) {

        User user = userRepository.findByPhonenumber(phonenumber)
                .orElseThrow(() -> new ResourceNotFoundException("User", "phonenumber", phonenumber));

        return user.getScore().longValue();
    }

    public Long getscoreval() {

        ScoreVal scoreVal = scoreValRepository.findById(1L)
                .orElseThrow(() -> new ObjectNotFoundException("ScoreVal not found"));
        return scoreVal.getValue();
    }

}
