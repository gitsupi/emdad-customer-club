package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    Optional<Company> findByUsername(String username);

    Boolean existsByUsername(String username);

}
