package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.entities.Book;
import com.carrynong.springbootlibrary.entities.Review;
import com.carrynong.springbootlibrary.services.ReviewService;
import com.carrynong.springbootlibrary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<Object> getAllReview() {
        return ResponseEntity.ok(reviewService.findAllReview());
    }

    @GetMapping("/search/findByBookId")
    public ResponseEntity<Object> getReviewsByBookId(
            @RequestParam() Long bookId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(reviewService.findAllReview());
        } else {
            Page<Review> reviews = reviewService.findByBookId(bookId, page, size);
            return ResponseEntity.ok(reviews);
        }
    }

}
