package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.UserEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface UserEventLogRepository extends JpaRepository<UserEventLog, Long> {

}
