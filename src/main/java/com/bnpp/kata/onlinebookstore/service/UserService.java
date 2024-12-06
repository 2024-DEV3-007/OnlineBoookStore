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

        String responseMessage ;
        boolean validResponse= false;

        if(userRepository.existsByUsername(registerRequest.getUsername ())){
            responseMessage =USER_EXISTS;
        } else {
            registerNewUser (registerRequest);
            responseMessage = REGISTER_SUCCESS;
            validResponse = true;
        }
        return UserLoginResponse.builder()
                .message(responseMessage)
                .validResponse (validResponse)
                .build();
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
