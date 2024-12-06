package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.ShoppingCart;
import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartRepository;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import com.bnpp.kata.onlinebookstore.store.CartResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Cart Details : No cart available , return empty list ")
    void getCartItems_noItemsAddedToCart_returnsEmptyList() {

        List<CartResponse> result = shoppingCartService.getCartItems (USERID);

        assertThat(result.isEmpty ());
    }

    @Test
    @DisplayName("Cart Details : Check user have entry in shopping cart")
    void getCartItems_checkUserHaveShoppingEcartEntry_returnsReponse() {

        userRepository.save(Users.builder().username(USERNAME).password(PASSWORD).build());
        Users user = userRepository.findByUsername (USERNAME);
        ShoppingCart shoppingCartUser = ShoppingCart.builder ().user (user).build();
        shoppingCartRepository.save(shoppingCartUser);

        List<CartResponse> result = shoppingCartService.getCartItems (USERID);

        assertThat(!result.isEmpty ());
    }
}
