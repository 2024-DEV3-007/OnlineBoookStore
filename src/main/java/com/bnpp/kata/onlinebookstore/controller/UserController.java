package com.bnpp.kata.onlinebookstore.controller;

import com.bnpp.kata.onlinebookstore.service.UserService;
import com.bnpp.kata.onlinebookstore.store.UserLoginRequest;
import com.bnpp.kata.onlinebookstore.store.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserLoginResponse> register(@RequestBody UserLoginRequest registerRequest) {

        UserLoginResponse response = userService.registerUser(registerRequest);

        return ResponseEntity.status(response.getValidResponse ()? HttpStatus.CREATED : HttpStatus.NOT_FOUND)
                .body(response);

    }
}