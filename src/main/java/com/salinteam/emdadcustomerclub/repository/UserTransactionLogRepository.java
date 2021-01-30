package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.UserEventLog;
import com.salinteam.emdadcustomerclub.model.UserTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface UserTransactionLogRepository extends JpaRepository<UserTransactionLog, Long> {

}
