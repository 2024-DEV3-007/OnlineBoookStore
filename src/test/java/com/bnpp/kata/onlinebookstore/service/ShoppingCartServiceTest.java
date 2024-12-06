package com.bnpp.kata.onlinebookstore.service;

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

    @Test
    @DisplayName("Cart Details : No cart available , return empty list ")
    void getCartItems_noItemsAddedToCart_returnsEmptyList() {

        List<CartResponse> result = shoppingCartService.getCartItems (USERID);

        assertThat(result.isEmpty ());
    }
}
