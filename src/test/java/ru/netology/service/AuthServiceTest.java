package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.dtos.LoginRequest;
import ru.netology.entity.UserEntity;
import ru.netology.repositories.TokenRepository;
import ru.netology.repositories.UserRepository;
import ru.netology.services.AuthService;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class AuthServiceTest {
    public static final String AUTH_TOKEN = "111";
    public static final String UNKNOWN_AUTH_TOKEN = "222";
    public static final String EXISTING_USER = "user";
    public static final String NOT_EXISTING_USER = "nouser";
    public static final String CORRECT_PASSWORD = "pass";

    private final UserRepository userRepository = createUserRepositoryMock();
    private final TokenRepository tokenRepository = createTokenRepositoryMock();

    private UserRepository createUserRepositoryMock() {
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(EXISTING_USER)).thenReturn(Optional.of(new UserEntity(EXISTING_USER, CORRECT_PASSWORD)));
        when(userRepository.findById(NOT_EXISTING_USER)).thenReturn(Optional.empty());
        return userRepository;
    }

    private TokenRepository createTokenRepositoryMock() {
        final TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
        when(tokenRepository.existsById(AUTH_TOKEN)).thenReturn(true);
        when(tokenRepository.existsById(UNKNOWN_AUTH_TOKEN)).thenReturn(false);
        return tokenRepository;
    }

    @Test
    void login() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> AuthService.login(new LoginRequest(EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void login_userNotFound() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> AuthService.login(new LoginRequest(NOT_EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void login_incorrectPassword() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> AuthService.login(new LoginRequest(EXISTING_USER, "qwerty")));
    }

    @Test
    void logout() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> AuthService.logout(AUTH_TOKEN));
    }

    @Test
    void checkToken() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertDoesNotThrow(() -> AuthService.checkToken(AUTH_TOKEN));
    }

    @Test
    void checkToken_failed() {
        final AuthService AuthService = new AuthService(userRepository, tokenRepository);
        Assertions.assertThrows(RuntimeException.class, () -> AuthService.checkToken(UNKNOWN_AUTH_TOKEN));
    }
}
