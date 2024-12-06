package com.bnpp.kata.onlinebookstore.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String registerUser (String username, String password) {
        return "Registration successful!";
    }
}
