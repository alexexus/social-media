package com.example.socialmedia.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    public Message sendMessage(Message message) {
        return repository.save(message);
    }
}
