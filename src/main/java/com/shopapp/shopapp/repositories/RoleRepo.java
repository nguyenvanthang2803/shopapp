package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {
    @Override
    Optional<Role> findById(Long aLong);
}
