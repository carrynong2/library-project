package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.entities.History;
import com.carrynong.springbootlibrary.services.HistoryService;
import com.carrynong.springbootlibrary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/histories")
@CrossOrigin(origins = "http://localhost:3000")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("")
    public ResponseEntity<Object> getAllHistory() {
        return ResponseEntity.ok(historyService.findAllHistory());
    }

    @GetMapping("/search/findBooksByUserEmail")
    public ResponseEntity<Object> getBooksByUserEmail(
            @RequestParam() String userEmail,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(historyService.findAllHistory());
        } else {
            Page<History> histories = historyService.findHistoryByUserEmail(userEmail, page, size);
            return ResponseEntity.ok(histories);
        }
    }
}
