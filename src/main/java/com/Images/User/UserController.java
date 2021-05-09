package com.Images.User;

import com.Images.User.models.Token;
import com.Images.User.models.Status;
import com.Images.User.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @PostMapping("/register")
    public Status registerUser(@RequestBody User newUser) {
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.isPresent() ) {
            System.out.println("User Already exists!");
            return Status.USER_ALREADY_EXISTS;
        }
        System.out.println("New user: " + newUser.toString());
        userRepository.save(newUser);
        return Status.SUCCESS;
    }

    @PostMapping("/login")
    public ResponseEntity<Status> loginUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (!existingUser.isPresent() ) {
            System.out.println("User does not exist!");
            return new ResponseEntity<Status>(Status.USER_DOES_NOT_EXIST, headers, HttpStatus.OK);
        }

        if (existingUser.get().getPassword().equals( user.getPassword())) {
            Optional<Token> existingToken =  tokenRepository.findByUserName(user.getUsername());
            if ( existingToken.isPresent()) {
                headers.set("Token", existingToken.get().getTokenValue());
            } else {
                Token t = new Token(existingUser.get());
                tokenRepository.save(t);
                headers.set("Token", t.getTokenValue());
            }
            return new ResponseEntity<Status>(Status.SUCCESS, headers, HttpStatus.OK);
        }
        return new ResponseEntity<Status>(Status.FAILURE, headers, HttpStatus.OK);
    }

    @PostMapping("/logout/user")
    public Status logUserOut( @RequestBody User user) {
        Optional<Token> token = tokenRepository.findByUserName(user.getUsername());
        if ( token.isPresent()) {
            tokenRepository.delete(token.get());
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }

    @PostMapping("/logout/token")
    public Status logUserOut( @RequestBody String tokenValue) {
        Optional<Token> token = tokenRepository.findByTokenValue(tokenValue);
        if ( token.isPresent()) {
            tokenRepository.delete(token.get());
            return Status.SUCCESS;
        }
        return Status.FAILURE;
    }


    @DeleteMapping("/all")
    public Status deleteUsers() {
        userRepository.deleteAll();
        return Status.SUCCESS;
    }


}
