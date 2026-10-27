package com.socialproject.dtos;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String message;
    private Long userId;
    private String accessToken; // İşte o meşhur "Bearer ey..." tokenı
}