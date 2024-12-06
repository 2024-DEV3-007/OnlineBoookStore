package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;

public class UserServiceTest {

    private UserService UserService;

    @BeforeEach
    public void setup() {
        UserService = new UserService();
    }

    @Test
    @DisplayName ("Register New User : User Registration Successful")
    void registerUser_newUserRegistration_returnsSuccessMessage() {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();

        UserLoginResponse result = UserService.registerUser (userLoginRequest);

        assertThat (result.getMessage ()).isEqualTo (REGISTER_SUCCESS);
    }
}
