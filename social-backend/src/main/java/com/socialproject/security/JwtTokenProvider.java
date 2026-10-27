package com.socialproject.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    // application.properties dosyasındaki değerleri buraya çekiyoruz
    @Value("${quest.app.jwtSecret}")
    private String APP_SECRET;

    @Value("${quest.app.jwtExpirationMs}")
    private long EXPIRES_IN;

    // --- 1. TOKEN ÜRETME METODU (Generate) ---
    // Kullanıcı login olduğunda bu çalışacak ve ona bir String verecek.
    public String generateJwtToken(Authentication auth) {
        // Giriş yapan kullanıcının detaylarını alıyoruz (Az önce yazdığımız sınıf)
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();

        // Token oluşturma süreci (Builder Deseni)
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId())) // Token'ın içine ID'yi gömüyoruz
                .setIssuedAt(new Date()) // Oluşturulma zamanı (Şu an)
                .setExpiration(new Date(new Date().getTime() + EXPIRES_IN)) // Bitiş zamanı (Şu an + 24 saat)
                // 512 YERİNE 256 YAPTIK BENİM ŞİFREMİ BEĞENEMDİ
                .signWith(SignatureAlgorithm.HS512, APP_SECRET) // Gizli anahtarla imzala
                .compact(); // Hepsini paketle ve String yap
    }

    // --- 2. TOKEN'DAN ID ÇÖZME METODU (Extract) ---
    // Gelen token'ı kırıp içindeki ID'yi okuyoruz.
    public Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(APP_SECRET) // Gizli anahtarı ver ki kilidi açsın
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject()); // ID'yi çek ve Long yap
    }

    // --- 3. TOKEN DOĞRULAMA METODU (Validate) ---
    // Bu token bizim mi? Süresi dolmuş mu? Bozuk mu?
    public boolean validateToken(String token) {
        try {
            // Token'ı ayrıştırmaya çalışıyoruz. Hata verirse sahtedir.
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return true; // Sorun yok, geçerli.
        } catch (SignatureException e) {
            System.out.println("Geçersiz JWT İmzası: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Geçersiz JWT Formatı: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Süresi Dolmuş JWT: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Desteklenmeyen JWT: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT Boş olamaz: " + e.getMessage());
        }
        return false; // Bir hata yakalandıysa false dön.
    }
}