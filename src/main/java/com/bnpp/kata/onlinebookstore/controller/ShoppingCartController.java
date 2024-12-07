package com.bnpp.kata.onlinebookstore.controller;

import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.service.ShoppingCartService;
import com.bnpp.kata.onlinebookstore.models.CartRequest;
import com.bnpp.kata.onlinebookstore.models.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${onlinebookstore.endpoint.cart}")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems () {

        Users currentUser =  getCurrentUser();

        List<CartResponse> cartItems = shoppingCartService.getCartItems (currentUser.getId ());

        return ResponseEntity.ok (cartItems);
    }

    public Users getCurrentUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Users) authentication.getPrincipal();
    }

    @PostMapping("${onlinebookstore.endpoint.updateCart}")
    public ResponseEntity<List<CartResponse>> updateCart(@RequestBody CartRequest cartRequests) {

        Users currentUser = getCurrentUser();

        List<CartResponse> response = shoppingCartService.updateCart(currentUser.getId(), cartRequests);

        return ResponseEntity.ok (response);
    }
}