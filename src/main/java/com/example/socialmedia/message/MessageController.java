package com.example.socialmedia.message;

import com.example.socialmedia.message.dto.NewMessage;
import com.example.socialmedia.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/message")
public class MessageController {

    private final MessageService service;
    private final UserService userService;

    @PostMapping
    public Message sendMessage(@RequestBody NewMessage newMessage) {
        Message message = new Message();
        message.setText(newMessage.getText());
        message.setFromId(userService.findById(newMessage.getFromId()));
        message.setToId(userService.findById(newMessage.getToId()));
        return service.sendMessage(message);
    }
}
