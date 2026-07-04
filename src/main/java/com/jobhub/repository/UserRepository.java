package com.jobhub.repository;

import com.jobhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    //optional avoids NullPointerException and is the recommended modern Java approach.
}
