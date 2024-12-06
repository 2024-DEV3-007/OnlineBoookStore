package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.ShoppingCart;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartRepository;
import com.bnpp.kata.onlinebookstore.store.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public List<CartResponse> getCartItems (Long userId) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElse(null);

        if (shoppingCart == null) {
            return Collections.emptyList();
        }

        List<CartResponse> responseList = new ArrayList<> ();
        responseList.add(CartResponse.builder().build());
        return responseList;
    }
}
