package com.salinteam.emdadcustomerclub.security;

import com.salinteam.emdadcustomerclub.exception.ResourceNotFoundException;
import com.salinteam.emdadcustomerclub.model.Company;
import com.salinteam.emdadcustomerclub.repository.CompanyRepository;
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
    //TODO
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Company company = companyRepository.findByUsername(usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Busiiness Unit not found with username or ;; : " + usernameOrEmail)
                );

        return CompanyPrincipal.create(company);
    }

    @Transactional
    //TODO
    public UserDetails loadUserById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", id)
        );

        return CompanyPrincipal.create(company);
    }
}