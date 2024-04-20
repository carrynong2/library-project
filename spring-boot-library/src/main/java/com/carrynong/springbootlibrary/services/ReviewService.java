package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.entities.Review;
import com.carrynong.springbootlibrary.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAllReview() {
        return reviewRepository.findAll();
    }

    public Page<Review> findByBookId(Long bookId, int page, int size) {
        if (size <= 0) {
            size = (int) reviewRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findByBookId(bookId, pageable);
    }

}
