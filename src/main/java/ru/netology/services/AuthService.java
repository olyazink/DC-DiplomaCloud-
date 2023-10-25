package ru.netology.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import ru.netology.dtos.LoginRequest;
import ru.netology.dtos.LoginResponse;
import ru.netology.entity.TokenEntity;
import ru.netology.entity.UserEntity;
import ru.netology.exceptions.AuthException;
import ru.netology.exceptions.InvalidCredentialsException;
import ru.netology.repositories.TokenRepository;
import ru.netology.repositories.UserRepository;

import java.util.Random;


@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final Random random = new Random();

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        final String loginFromRequest = loginRequest.getLogin();
        final UserEntity user = userRepository.findById(loginFromRequest).orElseThrow(() ->
                new InvalidCredentialsException("User with login " + loginFromRequest + " not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password for user " + loginFromRequest);
        }
        final String authToken = String.valueOf(random.nextLong());
        tokenRepository.save(new TokenEntity(authToken));
        logger.info("User " + loginFromRequest + " entered with token " + authToken);
        return new LoginResponse(authToken);
    }


    public void logout(String authToken) {
        tokenRepository.deleteById(authToken.replace("Bearer ", ""));
    }

    public void checkToken(String authToken) {
        if (!tokenRepository.existsById(authToken.replace("Bearer ", ""))) {
            throw new AuthException();
        }
    }
}
