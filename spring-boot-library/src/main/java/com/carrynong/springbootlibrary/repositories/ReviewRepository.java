package com.carrynong.springbootlibrary.repositories;

import com.carrynong.springbootlibrary.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByBookId(Long bookId, Pageable pageable);
    Review findByUserEmailAndBookId(String userEmail, Long bookId);
}
