package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import static com.bnpp.kata.onlinebookstore.constants.Constants.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserLoginResponse registerUser (UserLoginRequest registerRequest) {

        return UserLoginResponse.builder()
                .message(REGISTER_SUCCESS)
                .validResponse (VALID_RESPONSE)
                .build();
    }
}
