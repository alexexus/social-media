package com.example.socialmedia.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPost {

    @NotBlank
    private String text;

    @NotBlank
    private String title;

    @NotBlank
    private String url;

    @Positive
    private long authorId;
}
