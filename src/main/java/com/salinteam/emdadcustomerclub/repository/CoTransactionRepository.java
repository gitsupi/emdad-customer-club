package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.CoTransaction;
import com.salinteam.emdadcustomerclub.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@org.springframework.stereotype.Repository
public interface CoTransactionRepository extends JpaRepository<CoTransaction, Long> {


    List<CoTransaction> findByUsername(String username);

    List<CoTransaction> findByUsernameAndCompany(String username, Company company);

    Optional<CoTransaction> findByUsernameAndCompanyAndUserLevelId(String username, Company company, Long userLevelId);

    Boolean existsByUsername(String username);

}
