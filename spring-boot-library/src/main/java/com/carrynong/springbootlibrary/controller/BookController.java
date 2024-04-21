package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.services.BookService;
import com.carrynong.springbootlibrary.utils.ExtractJWT;
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
    public ResponseEntity<Object> getAllBook(@RequestParam(required = false) Integer page,
                                              @RequestParam(required = false) Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(bookService.findAllBook());
        } else {
            Page<Book> books = bookService.findAllBook(page, size);
            return ResponseEntity.ok(books);
        }
    }

    @GetMapping("/search/findByCategory")
    public ResponseEntity<Object> getBooksByCategory(
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
    public ResponseEntity<Object> getBooksByTitle(
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

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token,
            @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                        @RequestParam Long bookId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @GetMapping("/secure/currentLoans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

}
