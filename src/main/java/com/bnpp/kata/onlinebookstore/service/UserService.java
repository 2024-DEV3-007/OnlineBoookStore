package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import static com.bnpp.kata.onlinebookstore.constants.Constants.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserLoginResponse registerUser (UserLoginRequest registerRequest) {

        if(checkUserExists (registerRequest.getUsername())){
            return createResponse(USER_EXISTS, false);
        }

        registerNewUser(registerRequest);
        return createResponse(REGISTER_SUCCESS, true);
    }

    private UserLoginResponse createResponse(String message, boolean isValid) {

        return UserLoginResponse.builder()
                .message(message)
                .validResponse (isValid)
                .build();
    }
    private boolean checkUserExists (String username) {
        return userRepository.existsByUsername (username);
    }

    private void registerNewUser (UserLoginRequest registerRequest) {

        Users user = Users.builder ()
                .username (registerRequest.getUsername ())
                .firstname (registerRequest.getFirstName ())
                .lastname (registerRequest.getLastName ())
                .password (registerRequest.getPassword ())
                .build ();

        userRepository.save (user);
    }
}
