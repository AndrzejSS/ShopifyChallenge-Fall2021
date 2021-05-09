package com.Images.User;

import com.Images.Image.models.Response;
import com.Images.User.models.Token;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    final private TokenRepository tokenRepository;

    public boolean authenticate(String tokenValue, String username) {
        Optional<Token> token = tokenRepository.findByTokenValue(tokenValue);
        if (token.isPresent() && token.get().getUser().getUsername() == username) {
            return true;
        }
        return false;
    }

    public String getUsenameFromToken(String tokenValue) {
        Optional<Token> token = tokenRepository.findByTokenValue(tokenValue);
        if ( token.isPresent()) {
            return token.get().getUser().getUsername();
        }
        return "";
    }



}
