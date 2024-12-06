package com.bnpp.kata.onlinebookstore.service;

import com.bnpp.kata.onlinebookstore.entity.*;
import com.bnpp.kata.onlinebookstore.exception.UserNotFoundException;
import com.bnpp.kata.onlinebookstore.repository.*;
import com.bnpp.kata.onlinebookstore.store.BookDetails;
import com.bnpp.kata.onlinebookstore.store.BookRequest;
import com.bnpp.kata.onlinebookstore.store.CartRequest;
import com.bnpp.kata.onlinebookstore.store.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.bnpp.kata.onlinebookstore.constants.Constants.*;
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
    private final BookRepository bookRepository;
    private final BookHistoryDetailsRepository bookHistoryDetailsRepository;
    private final ShoppingHistoryRepository shoppingHistoryRepository;

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

    @Transactional
    public List<CartResponse> updateCart(Long userId, CartRequest cartRequests) {

        ShoppingCart cart = getOrCreateShoppingCart(userId);

        if (cartRequests.getItems ().isEmpty()) {
            handleEmptyCart (cart);
            return new ArrayList<>();
        }

        if (cartRequests.getOrdered ()) {
            addToHistoryTable(cart.getUser().getId(), cartRequests.getItems());
        }

        return updateCartItems(cart, cartRequests.getItems ());
    }

    @Transactional
    private void addToHistoryTable(Long userId, List<BookRequest> bookRequests) {

        List<BookHistoryDetail> bookHistory = new ArrayList<>();
        for (BookRequest request : bookRequests) {
            BookHistoryDetail detail = BookHistoryDetail.builder()
                    .bookId(request.getBookId())
                    .quantity(request.getQuantity())
                    .build();
            bookHistory.add(detail);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException (USER_NOT_EXISTS));

        bookHistory.forEach(bookHistoryDetail -> bookHistoryDetailsRepository.save(bookHistoryDetail));

        shoppingHistoryRepository.save(ShoppingHistory.builder ()
                        .user (user).bookDetails (bookHistory).build ());
    }

    private void handleEmptyCart (ShoppingCart cart) {
        shoppingCartItemRepository.deleteByShoppingCartId(cart.getId());
        shoppingCartRepository.deleteById(cart.getId ());
    }
    private List<CartResponse> updateCartItems(ShoppingCart cart, List<BookRequest> cartRequests) {

        shoppingCartItemRepository.deleteByShoppingCartId(cart.getId());

        return cartRequests.stream()
                .map(request -> {
                    ShoppingCartItem newItem = createCartItem(cart, request);
                    shoppingCartItemRepository.save(newItem);
                    return mapToCartResponse(newItem, request);
                })
                .collect(Collectors.toList());
    }

    private CartResponse mapToCartResponse(ShoppingCartItem cartItem, BookRequest request) {

        return CartResponse.builder()
                .id(cartItem.getId())
                .book(createBookDetails(cartItem.getBook()))
                .quantity(request.getQuantity())
                .build();
    }

    private ShoppingCartItem createCartItem(ShoppingCart cart, BookRequest request) {

        Books book = getBookById(request.getBookId());
        return ShoppingCartItem.builder ().shoppingCart (cart)
                .book (book).quantity (request.getQuantity()).build ();
    }
    private Books getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException (BOOK_NOT_FOUND));
    }
    private ShoppingCart getOrCreateShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> createNewShoppingCart(userId));
    }

    private ShoppingCart createNewShoppingCart(Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException (USER_NOT_EXISTS));
        ShoppingCart newCart = ShoppingCart.builder()
                .user(user)
                .build();

        return shoppingCartRepository.save(newCart);
    }
}
