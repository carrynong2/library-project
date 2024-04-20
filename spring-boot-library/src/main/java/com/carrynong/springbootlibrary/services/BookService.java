package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    public Page<Book> findAllBook(int page, int size) {
        if (size <= 0) {
            size = (int) bookRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    public Page<Book> findByCategory(String category, int page, int size) {
        if (size <= 0) {
            size = (int) bookRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByCategory(category, pageable);
    }

    public Page<Book> findByTitle(String title, int page, int size) {
        if (size <= 0) {
            size = (int) bookRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByTitleContaining(title, pageable);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id " + id + "DOES NOT EXIST !!!")
        );
    }

}
