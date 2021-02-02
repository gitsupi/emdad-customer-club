package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.Company;
import com.salinteam.emdadcustomerclub.model.Role;
import com.salinteam.emdadcustomerclub.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByName(RoleName name);

}
