package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.services.BookService;
import com.carrynong.springbootlibrary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<Object> findAllBook(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(bookService.findAllBook());
        } else {
            Page<Book> books = bookService.findAllBook(page, size);
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/search/findByCategory")
    public ResponseEntity<Object> findByCategory(
                                                @RequestParam() String category,
                                                @RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(bookService.findAllBook());
        } else {
            Page<Book> books = bookService.findByCategory(category, page, size);
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/search/findByTitleContaining")
    public ResponseEntity<Object> findByTitle(
            @RequestParam() String title,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(bookService.findAllBook());
        } else {
            Page<Book> books = bookService.findByTitle(title, page, size);
            return ResponseEntity.ok(books);
        }
    }

}
