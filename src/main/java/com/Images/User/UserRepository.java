package com.Images.User;

import com.Images.User.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}