package com.example.socialmedia.message.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewMessage {

    @NotNull
    private long fromId;

    @NotNull
    private long toId;

    @NotEmpty
    @Size(min = 1, max = 4000)
    private String text;
}
