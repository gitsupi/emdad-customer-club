package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.CoEvent;
import com.salinteam.emdadcustomerclub.model.Company;
import com.salinteam.emdadcustomerclub.model.GroupLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface CoEventRepository extends JpaRepository<CoEvent, Long> {

    Optional<CoEvent> findByEventIdAndCompany(String eventId, Company company);
    Optional<CoEvent> findByEventIdAndCompanyAndGroupLevel(String eventId, Company company, GroupLevel groupLevel);


}
