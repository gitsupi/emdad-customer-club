package com.salintream.emdadcustomerclub.repository;

import com.salintream.emdadcustomerclub.model.CoEvent;
import com.salintream.emdadcustomerclub.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface CoEventRepository extends JpaRepository<CoEvent, Long> {


    List<CoEvent> findByUsername(String username);

    Optional<CoEvent> findByUsernameAndCompany(String username, Company company);

    Boolean existsByUsername(String username);

}
