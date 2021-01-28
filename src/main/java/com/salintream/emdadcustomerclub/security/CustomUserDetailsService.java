package com.salintream.emdadcustomerclub.security;

import com.salintream.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salintream.emdadcustomerclub.model.Company;
import com.salintream.emdadcustomerclub.repository.CompanyRepository;
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
    CompanyRepository companyRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Company company = companyRepository.findByUsername(usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Busiiness Unit not found with username or ;; : " + usernameOrEmail)
                );

        return UserPrincipal.create(company);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(company);
    }
}