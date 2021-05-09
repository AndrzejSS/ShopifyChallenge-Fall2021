package com.Images.User.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public
class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenValue;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    public Token(User user) {
        this.user = user;
        this.tokenValue = UUID.randomUUID().toString();
    }

    public Token(Long id, String tokenValue, User user) {
        this.id = id;
        this.tokenValue = tokenValue;
        this.user = user;
    }

}