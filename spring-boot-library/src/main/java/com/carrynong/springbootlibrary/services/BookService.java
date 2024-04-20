package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
