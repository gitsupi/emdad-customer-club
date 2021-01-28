package com.salintream.emdadcustomerclub.repository;

import com.salintream.emdadcustomerclub.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {


    Optional<Business> findByUsername(String username);

    Boolean existsByUsername(String username);

}
