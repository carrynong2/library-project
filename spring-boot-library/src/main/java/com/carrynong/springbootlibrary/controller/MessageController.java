package com.carrynong.springbootlibrary.controller;

import com.carrynong.springbootlibrary.dtos.request.AdminQuestionRequest;
import com.carrynong.springbootlibrary.entities.History;
import com.carrynong.springbootlibrary.entities.Message;
import com.carrynong.springbootlibrary.services.MessageService;
import com.carrynong.springbootlibrary.utils.ExtractJWT;
import com.carrynong.springbootlibrary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messageService.postMessage(messageRequest, userEmail);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllMessage() {
        return ResponseEntity.ok(messageService.findAllMessage());
    }

    @GetMapping("/search/findByUserEmail")
    public ResponseEntity<Object> getMessagesByUserEmail(
            @RequestParam() String userEmail,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(messageService.findAllMessage());
        } else {
            Page<Message> messages = messageService.findMessageByUserEmail(userEmail, page, size);
            return ResponseEntity.ok(messages);
        }
    }

    @GetMapping("/search/findByClosed")
    public ResponseEntity<Object> getMessagesByClosed(
            @RequestParam() Boolean closed,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {
        if (Utils.validatePageable(page, size)) {
            return ResponseEntity.ok(messageService.findAllMessage());
        } else {
            Page<Message> messages = messageService.findMessageByClosed(closed, page, size);
            return ResponseEntity.ok(messages);
        }
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        messageService.putMessage(adminQuestionRequest, userEmail);
    }

}
