package com.salinteam.emdadcustomerclub.repository;

import com.salinteam.emdadcustomerclub.model.GroupLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupLevelRepository extends JpaRepository<GroupLevel,Long> {

    Optional<GroupLevel> findGroupLevelById(Long id);
}
