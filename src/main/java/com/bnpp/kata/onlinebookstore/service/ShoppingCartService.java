package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.Books;
import com.bnpp.kata.onlinebookstore.entity.ShoppingCart;
import com.bnpp.kata.onlinebookstore.entity.ShoppingCartItem;
import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartItemRepository;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartRepository;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import com.bnpp.kata.onlinebookstore.store.BookDetails;
import com.bnpp.kata.onlinebookstore.store.CartRequest;
import com.bnpp.kata.onlinebookstore.store.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final UserRepository userRepository;

    public List<CartResponse> getCartItems (Long userId) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElse(null);

        if (shoppingCart == null) {
            return Collections.emptyList();
        }

        return shoppingCartItemRepository.findByShoppingCartId(shoppingCart.getId())
                .stream()
                .map(item -> createCartResponse(item, createBookDetails(item.getBook())))
                .collect(Collectors.toList());
    }

    private CartResponse createCartResponse (ShoppingCartItem item, BookDetails bookdetails) {

        return CartResponse.builder()
                .id(item.getId())
                .book(bookdetails)
                .quantity(item.getQuantity())
                .build();
    }
    private BookDetails createBookDetails (Books item) {

        return BookDetails.builder ().id (item.getId ())
                .title (item.getTitle ())
                .author (item.getAuthor ())
                .price (item.getPrice ()).build ();

    }

    public List<CartResponse> updateCart(Long userId, CartRequest cartRequests) {

        ShoppingCart cart = getOrCreateShoppingCart(userId);

        List<CartResponse> responseList = new ArrayList<> ();
        responseList.add(CartResponse.builder().build());
        return responseList;
    }

    private ShoppingCart getOrCreateShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> createNewShoppingCart(userId));
    }

    private ShoppingCart createNewShoppingCart(Long userId) {

        Users user = userRepository.findById(userId).get ();
        ShoppingCart newCart = ShoppingCart.builder()
                .user(user)
                .build();

        return shoppingCartRepository.save(newCart);
    }
}
