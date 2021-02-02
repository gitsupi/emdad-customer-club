package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.UserEventLog;
import com.salinteam.emdadcustomerclub.model.UserTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface UserTransactionLogRepository extends JpaRepository<UserTransactionLog, Long> {
    Optional<UserTransactionLog> findUserTransactionLogByUser_Phonenumber(String phonenumber);
    List<UserTransactionLog> findUserTransactionLogsByUser_Phonenumber(String phonenumber);

}
