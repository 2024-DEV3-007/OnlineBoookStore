package com.bnpp.kata.onlinebookstore.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserServiceTest {

    private UserService UserService;

    @BeforeEach
    public void setup() {
        UserService = new UserService();
    }

    @Test
    @DisplayName ("Register New User : User Registration Successful")
    void registerUser_newUserRegistration_returnsSuccessMessage() {

        String result = UserService.registerUser("abc","abc");

        assertThat(result).isEqualTo("Registration successful!");
    }
}
