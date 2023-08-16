package com.example.socialmedia.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    private String name;
    private String email;
    private String password;
    private String confirmedPassword;
}
