package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.Books;
import com.bnpp.kata.onlinebookstore.entity.ShoppingCart;
import com.bnpp.kata.onlinebookstore.entity.ShoppingCartItem;
import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.exception.UserNotFoundException;
import com.bnpp.kata.onlinebookstore.repository.BookRepository;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartItemRepository;
import com.bnpp.kata.onlinebookstore.repository.ShoppingCartRepository;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import com.bnpp.kata.onlinebookstore.store.BookRequest;
import com.bnpp.kata.onlinebookstore.store.CartRequest;
import com.bnpp.kata.onlinebookstore.store.CartResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void cleanUp() {

        shoppingCartItemRepository.deleteAll ();
        shoppingCartRepository.deleteAll();
        bookRepository.deleteAll ();
        userRepository.deleteAll ();
    }
    @Test
    @DisplayName("Cart Details : No cart available , return empty list ")
    void getCartItems_noItemsAddedToCart_returnsEmptyList() {

        List<CartResponse> result = shoppingCartService.getCartItems (USERID);

        assertThat(result.isEmpty ());
    }

    @Test
    @DisplayName("Cart Details : Check user have entry in shopping cart")
    void getCartItems_checkUserHaveShoppingCartEntry_returnsResponse() {

        Users user = createUserRepo();
        ShoppingCart shoppingCartUser = createShoppingCartRepo(user);

        List<CartResponse> result = shoppingCartService.getCartItems (user.getId ());

        assertThat(!result.isEmpty ());
    }

    @Test
    @DisplayName("Cart Details : Fetch the cart details of the user")
    void getCartItems_fetchTheCartDetailsOfTheUser_returnsListOfCartItems() {

        Users user = createUserRepo();
        ShoppingCart shoppingCartUser = createShoppingCartRepo(user);
        Books books = createBooksRepo();
        ShoppingCartItem shoppingCartItem = createShoppingCartItemRepo(shoppingCartUser,books);

        List<CartResponse> result = shoppingCartService.getCartItems (user.getId ());

        assertThat(result.get (0).getBook ().getTitle ()).isEqualTo (BOOK_NAME);
    }

    private Users createUserRepo(){
        return userRepository.save(Users.builder().username(USERNAME).password(PASSWORD).build());
    }

    private ShoppingCart createShoppingCartRepo(Users user){
        return shoppingCartRepository.save(ShoppingCart.builder ().user (user).build());
    }

    private Books createBooksRepo(){
        return bookRepository.save (Books.builder ().title (BOOK_NAME).author (FIRSTNAME).price (PRICE).build ());
    }

    private ShoppingCartItem createShoppingCartItemRepo(ShoppingCart shoppingCartUser , Books books){
        return  shoppingCartItemRepository.save(ShoppingCartItem.builder ().shoppingCart (shoppingCartUser)
                .book (books).quantity (BOOK_COUNT).build ());
    }

    @Test
    @DisplayName("Update Cart Details : Return the cart if the user have one")
    void updateCart_userHaveACart_returnsUserCart() {

        Users user = createUserRepo();
        ShoppingCart shoppingCartUser = createShoppingCartRepo(user);
        List<BookRequest> bookrequestList = createBookRequest();
        CartRequest cartRequest = CartRequest.builder()
                .items (bookrequestList).ordered (false).build ();

        List<CartResponse> result = shoppingCartService.updateCart (user.getId (),cartRequest);

        assertThat(!result.isEmpty ());
    }

    private List<BookRequest> createBookRequest(){
        List<BookRequest> bookrequestList = new ArrayList<> ();
        BookRequest bookrequest = BookRequest.builder ().bookId (BOOKID).quantity (BOOK_COUNT).build ();
        bookrequestList.add (bookrequest) ;
        return bookrequestList;
    }

    @Test
    @DisplayName("Update Cart Details : Create cart if not available")
    void updateCart_createShoppingCartIfNotAvailable_returnsUserCart() {

        Users user = createUserRepo();
        List<BookRequest> bookrequestList = createBookRequest();
        CartRequest cartRequest = CartRequest.builder()
                .items (bookrequestList).ordered (false).build ();

        List<CartResponse> result = shoppingCartService.updateCart (user.getId (),cartRequest);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId (user.getId ()).get ();
        assertThat(shoppingCart.getUser ().getUsername ()).isEqualTo (USERNAME);
    }
    @Test
    @DisplayName("Update Cart Details : If User is not available throw error")
    void updateCart_userNotAvailable_throwUserNotFoundException() {

        List<BookRequest> bookrequestList = createBookRequest();
        CartRequest cartRequest = CartRequest.builder()
                .items (bookrequestList).ordered (false).build ();

        assertThrows(UserNotFoundException.class, () -> shoppingCartService.updateCart (USERID,cartRequest));
    }

    @Test
    @DisplayName("Update Cart Details : Return empty list if the request is empty")
    void updateCart_checkIfTheRequestIsEmpty_returnsEmptyList() {

        Users user = createUserRepo();
        CartRequest cartRequest = CartRequest.builder()
                .items (Collections.EMPTY_LIST)
                .ordered (false).build ();

        List<CartResponse> result = shoppingCartService.updateCart (user.getId (),cartRequest);

        assertThat(result.isEmpty ());
    }
}
