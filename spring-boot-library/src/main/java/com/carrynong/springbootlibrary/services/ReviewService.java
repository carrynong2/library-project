package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.dtos.request.ReviewRequest;
import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.entities.Review;
import com.carrynong.springbootlibrary.repositories.BookRepository;
import com.carrynong.springbootlibrary.repositories.ReviewRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
        if (validateReview != null) {
            throw new Exception("Review already created");
        }
        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long bookId) {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateReview != null) {
            return true;
        } else {
            return false;
        }
    }

}
