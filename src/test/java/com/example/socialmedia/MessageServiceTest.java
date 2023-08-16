package com.example.socialmedia;

import com.example.socialmedia.message.Message;
import com.example.socialmedia.message.MessageRepository;
import com.example.socialmedia.message.MessageService;
import com.example.socialmedia.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService service;
    @Mock
    private MessageRepository repository;

    @Test
    void sendMessage() {
        Message message = Message.builder()
                .id(1L)
                .fromId(User.builder().id(1L).build())
                .toId(User.builder().id(2L).build())
                .text("text")
                .build();
        when(repository.save(any(Message.class))).thenReturn(message);

        Message actual = service.sendMessage(message);

        assertThat(actual).usingRecursiveComparison().isEqualTo(message);
        verify(repository, times(1)).save(message);
        verifyNoMoreInteractions(repository);
    }
}
