package com.socialproject.dtos;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String username;
    private String password;
}