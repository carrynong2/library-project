package com.carrynong.springbootlibrary.services;

import com.carrynong.springbootlibrary.dtos.request.AdminQuestionRequest;
import com.carrynong.springbootlibrary.entities.History;
import com.carrynong.springbootlibrary.entities.Message;
import com.carrynong.springbootlibrary.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public List<Message> findAllMessage() {
        return messageRepository.findAll();
    }

    public Page<Message> findMessageByUserEmail(String userEmail, int page, int size) {
        if (size <= 0) {
            size = (int) messageRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByUserEmail(userEmail, pageable);
    }

    public Page<Message> findMessageByClosed(Boolean closed, int page, int size) {
        if (size <= 0) {
            size = (int) messageRepository.count();
        }
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByClosed(closed, pageable);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found");
        }

        message.get().setUserEmail(userEmail);
        message.get().setResponse(adminQuestionRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }
}
