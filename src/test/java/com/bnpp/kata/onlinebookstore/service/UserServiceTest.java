package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName ("Register New User : User Registration Successful")
    void registerUser_newUserRegistration_returnsSuccessMessage() {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();

        UserLoginResponse result = userService.registerUser (userLoginRequest);

        assertThat (result.getMessage ()).isEqualTo (REGISTER_SUCCESS);
    }

    @Test
    @DisplayName ("Register New User : User details should be saved in db")
    void registerUser_userDetailsSaveInDB_returnsSuccessResponse() {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();

        UserLoginResponse result = userService.registerUser (userLoginRequest);

        Users savedUser = userRepository.findByUsername(USERNAME);
        assertThat (savedUser.getUsername ()).isEqualTo (USERNAME);
    }
}
