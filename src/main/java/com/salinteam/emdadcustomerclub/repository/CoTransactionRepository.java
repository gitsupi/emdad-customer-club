package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.CoTransaction;
import com.salinteam.emdadcustomerclub.model.Company;
import com.salinteam.emdadcustomerclub.model.GroupLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@org.springframework.stereotype.Repository
public interface CoTransactionRepository extends JpaRepository<CoTransaction, Long> {

    Optional<CoTransaction> findByTransactionIdAndCompanyAndGroupLevel(String transId, Company company, GroupLevel userLevelId);

}
