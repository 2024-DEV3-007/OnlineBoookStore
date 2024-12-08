package com.bnpp.kata.onlinebookstore.controller;

import com.bnpp.kata.onlinebookstore.entity.Users;
import com.bnpp.kata.onlinebookstore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Base64;
import static com.bnpp.kata.onlinebookstore.constants.TestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll ();
    }
    @Test
    @DisplayName("Fetch Available Books")
    void getAllBooks_fetchBooksFromDB_returnsListOkBooks() throws Exception {

        Users user = userRepository.save(Users.builder().username(USERNAME).password(passwordEncoder.encode(PASSWORD)).build());
        String authToken = BASIC + Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
        mockMvc.perform(get(BOOK_FETCH_API)
                        .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath ("$", hasSize(BOOK_COUNT))) ;
    }
}
