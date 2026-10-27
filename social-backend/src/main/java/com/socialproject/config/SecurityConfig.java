package com.socialproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.socialproject.security.JwtAuthenticationEntryPoint;
import com.socialproject.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint handler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint handler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.handler = handler;
    }

    // 1. BEAN: Şifreleme Makinesi (BCrypt)
    // Şifreleri veritabanına "1234" diye değil, "$2a$10$dXJ..." gibi karmaşık kaydeder.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. BEAN: Giriş Yöneticisi (AuthenticationManager)
    // Kullanıcı adı/şifre kontrolünü yapan ana müdür. Controller'da lazım olacak.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 3. BEAN: Güvenlik Kuralları (FilterChain) - EN ÖNEMLİSİ! 🚧
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // React bağlantısı için izin
            .csrf(csrf -> csrf.disable()) // CSRF korumasını kapat (Token kullanıyoruz gerek yok)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(handler)) // Hata olursa buraya git
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Session tutma (REST API)
            
            .authorizeHttpRequests(auth -> auth
                // A) HERKESE AÇIK YOLLAR (Turnike Yok)
                .requestMatchers("/api/auth/**").permitAll() // Login ve Register
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll() // Post okumak serbest olsun mu? (Karar senin)
                .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                
                // B) KİLİTLİ YOLLAR (Turnike Var)
                .anyRequest().authenticated() // Geri kalan her şey için Token şart!
            );

        // Filtreyi ekle: Username/Password kontrolünden ÖNCE bizim JWT filtresi çalışsın.
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    // 4. BEAN: CORS Ayarları (Frontend için hazırlık)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Şimdilik herkese izin ver (React için localhost:3000 olacak sonra)
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    // Helper method for CORS
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}