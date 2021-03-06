package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhonenumber(String phonenumber);

    //todo
//    Optional<User> findByPhonenumberAndCompanies(String phonenumber, List<Company> companies);


    Optional<User> findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByPhonenumber(String phonenumber);

    Boolean existsByEmail(String email);
}
