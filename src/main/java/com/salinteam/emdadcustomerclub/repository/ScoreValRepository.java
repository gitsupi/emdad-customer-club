package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.ScoreVal;
import com.salinteam.emdadcustomerclub.model.User;
import com.salinteam.emdadcustomerclub.model.UserEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface ScoreValRepository extends JpaRepository<ScoreVal, Long> {

}
