package com.example.ElectronicStore.Repository;

import com.example.ElectronicStore.Entity.Role;
import com.example.ElectronicStore.Entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleEnum name);
}
