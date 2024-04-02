package com.smartera.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartera.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);

}
