package com.socialproject.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        // Yetkisiz giriş denemesinde "401 Unauthorized" hatası ve mesaj dön.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Yetkisiz Giriş! Lütfen giriş yapın. (Hata: " + authException.getMessage() + ")");
    }
}