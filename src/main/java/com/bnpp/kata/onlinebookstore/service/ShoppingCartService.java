package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.store.CartResponse;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartService {
    public List<CartResponse> getCartItems (Long userId) {
        return Collections.emptyList();
    }
}
