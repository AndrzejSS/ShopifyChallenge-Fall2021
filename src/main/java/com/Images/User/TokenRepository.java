package com.Images.User;

import com.Images.User.models.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {

    @Query("SELECT t FROM Token t WHERE t.user.username = :userName")
    Optional<Token> findByUserName(@Param("userName") String username);

    Optional<Token> findByTokenValue(String tokenValue);
}