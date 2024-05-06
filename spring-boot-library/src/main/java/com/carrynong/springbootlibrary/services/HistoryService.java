package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.entities.History;
import com.carrynong.springbootlibrary.entities.Review;
import com.carrynong.springbootlibrary.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public List<History> findAllHistory() {
        return historyRepository.findAll();
    }

    public Page<History> findHistoryByUserEmail(String userEmail, int page, int size) {
        if (size <= 0) {
            size = (int) historyRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return historyRepository.findBooksByUserEmail(userEmail, pageable);
    }

}
