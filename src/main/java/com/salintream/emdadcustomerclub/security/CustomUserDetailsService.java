package com.salintream.emdadcustomerclub.security;

import com.salintream.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salintream.emdadcustomerclub.model.Business;
import com.salintream.emdadcustomerclub.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    BusinessRepository businessRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Business business = businessRepository.findByUsername(usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Busiiness Unit not found with username or ;; : " + usernameOrEmail)
                );

        return UserPrincipal.create(business);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Business business = businessRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(business);
    }
}