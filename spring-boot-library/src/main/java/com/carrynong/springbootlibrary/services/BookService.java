package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.entities.Checkout;
import com.carrynong.springbootlibrary.repositories.BookRepository;
import com.carrynong.springbootlibrary.repositories.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;

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

    public Book checkoutBook(String userEmail, Long bookId) throws Exception  {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book does't exist or already checked out by user");
        }
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.saveAndFlush(book.get());

        Checkout checkout = new Checkout(userEmail,
                                        LocalDate.now().toString(),
                                        LocalDate.now().plusDays(7).toString(),
                                        book.get().getId());

        checkoutRepository.saveAndFlush(checkout);
        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout != null) {
            return true;
        } else {
            return false;
        }
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

}
