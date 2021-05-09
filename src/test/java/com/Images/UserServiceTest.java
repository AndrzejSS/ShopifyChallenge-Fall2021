package com.Images;

import com.Images.User.TokenRepository;
import com.Images.User.UserService;
import com.Images.User.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;
import com.Images.User.models.Token;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private TokenRepository tokenRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(tokenRepository);
    }

    @Test
    public void testAuthenticate() {
        when(tokenRepository.findByTokenValue("123")).thenReturn(Optional.of(new Token(1L, "123",
                new User("test", "test"))));

        boolean result = userService.authenticate("123", "test");
        assertThat(result).isTrue();
        result = userService.authenticate("1", "test");
        assertThat(result).isFalse();
        result = userService.authenticate("123", "test2");
        assertThat(result).isFalse();
    }

    @Test
    public void testGetUsernameFromToken() {
        when(tokenRepository.findByTokenValue("123")).thenReturn(Optional.of(new Token(1L, "123",
                new User("test", "test"))));

        String result = userService.getUsenameFromToken("123");
        assertThat(result).isEqualTo("test");
        result = userService.getUsenameFromToken("321");
        assertThat(result).isEqualTo("");


    }
}
