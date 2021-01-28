package com.salintream.emdadcustomerclub.repository;

import com.salintream.emdadcustomerclub.model.Business;
import com.salintream.emdadcustomerclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Business> findByEmail(String email);

    Optional<Business> findByUsernameOrEmail(String username, String email);

    List<Business> findByIdIn(List<Long> userIds);

    Optional<Business> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
