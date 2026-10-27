package com.socialproject.dtos;

import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    private String username;
    private String password;
    // İleride email, firstname vs eklersin
}