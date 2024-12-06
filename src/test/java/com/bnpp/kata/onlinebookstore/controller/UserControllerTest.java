package com.bnpp.kata.onlinebookstore.controller;

import com.bnpp.kata.onlinebookstore.service.UserService;
import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Test
    @DisplayName ("Register API : Return Valid Response for new user registration")
    void register_newUserRegistration_shouldReturnUserLoginResponse() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();
        when(userService.registerUser(userLoginRequest)).thenReturn( UserLoginResponse.builder()
                .message(REGISTER_SUCCESS)
                .validResponse (true)
                .build());


        mockMvc.perform(post(REGISTER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginRequest)))
                .andExpect(status().isCreated())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName ("Register API : Return InValid Response for existing user")
    void register_existingUserRegistration_shouldReturnInvalidResponse() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();
        when(userService.registerUser(userLoginRequest)).thenReturn( UserLoginResponse.builder()
                .message(REGISTER_SUCCESS)
                .validResponse (false)
                .build());

        mockMvc.perform(post(REGISTER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginRequest)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName ("Login API : Return Valid Response for success user login")
    void register_userLoginSuccess_shouldReturnUserLoginResponse() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (USERNAME)
                .firstName (FIRSTNAME)
                .lastName (LASTNAME)
                .password (PASSWORD).build ();
        when(userService.loginUser(userLoginRequest)).thenReturn( UserLoginResponse.builder()
                .message(REGISTER_SUCCESS)
                .validResponse (true)
                .build());


        mockMvc.perform(post(LOGIN_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginRequest)))
                .andExpect(status().isOk ())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()));
    }

    @Test
    @DisplayName ("Login API : Invalid credentials should throw exception")
    void register_userInvalidCredentials_shouldReturnUserResponse() throws Exception {

        UserLoginRequest userLoginRequest = UserLoginRequest.builder ()
                .username (EMPTY)
                .password (PASSWORD).build ();

        when(userService.loginUser(userLoginRequest)).thenReturn( UserLoginResponse.builder()
                .message(REGISTER_SUCCESS)
                .validResponse (false)
                .build());

        mockMvc.perform(post(LOGIN_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLoginRequest)))
                .andExpect(status().isUnauthorized ())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()));
    }

}
