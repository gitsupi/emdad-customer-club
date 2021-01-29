package com.salintream.emdadcustomerclub.repository;

import com.salintream.emdadcustomerclub.model.CoEvent;
import com.salintream.emdadcustomerclub.model.Company;
import com.salintream.emdadcustomerclub.model.UserEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface UserEventLogRepository extends JpaRepository<UserEventLog, Long> {

}
