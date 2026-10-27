package com.socialproject.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // 1. İstekten Token'ı ayıkla (Helper metot aşağıda)
            String jwtToken = getJwtFromRequest(request);

            // 2. Token dolu mu ve Bizim Darphaneden mi çıkmış (Validate)?
            if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                
                // 3. Token sağlamsa, içindeki ID'yi çöz
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);

                // 4. O ID'ye ait kullanıcıyı veritabanından getir
                UserDetails userDetails = userDetailsService.loadUserDetailsById(id);

                // 5. KİMLİK KARTI OLUŞTUR (Authentication)
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 6. SİSTEME GİRİŞ YAPTIR (Context'e kaydet)
                    // Artık Spring Security "Bu adam içeride" diyecek.
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {
            // Hata olursa log bas ama sistemi durdurma, belki adamın token'ı yoktur, anonim takılacaktır.
            return; 
        }

        // 7. Yola Devam Et (Controller'a git)
        filterChain.doFilter(request, response);
    }

    // --- YARDIMCI METOT: Token'ı Header'dan Söküp Alır ---
    private String getJwtFromRequest(HttpServletRequest request) {
        // Header'da "Authorization" kısmına bak: "Bearer eyJhbGciOi..."
        String bearerToken = request.getHeader("Authorization");
        
        // Eğer doluysa ve "Bearer " ile başlıyorsa, baştaki "Bearer " kısmını kes, sadece kodu al.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}