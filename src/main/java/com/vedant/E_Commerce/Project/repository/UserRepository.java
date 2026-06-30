package com.vedant.E_Commerce.Project.repository;

import com.vedant.E_Commerce.Project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

 Optional<User> findByEmail(String email);
}