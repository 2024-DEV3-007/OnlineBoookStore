package com.bnpp.kata.onlinebookstore.controller;

import com.bnpp.kata.onlinebookstore.entity.Books;
import com.bnpp.kata.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Books> getAllBooks () {
        return bookService.getAllBooks ();
    }
}