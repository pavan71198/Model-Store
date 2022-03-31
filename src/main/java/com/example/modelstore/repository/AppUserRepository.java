package com.example.modelstore.repository;

import com.example.modelstore.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);
}
