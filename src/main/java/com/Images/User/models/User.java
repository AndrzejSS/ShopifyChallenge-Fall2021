package com.Images.User.models;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;


    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
