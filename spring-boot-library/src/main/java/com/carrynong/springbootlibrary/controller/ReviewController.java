package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.dtos.request.ReviewRequest;
import com.carrynong.springbootlibrary.entities.Review;
import com.carrynong.springbootlibrary.services.ReviewService;
import com.carrynong.springbootlibrary.utils.ExtractJWT;
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

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

    @GetMapping("/secure/user/book")
    public boolean reviewBookByUser(@RequestHeader(value = "Authorization") String token,
                                    @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

}
