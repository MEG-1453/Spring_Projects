package com.socialproject.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialproject.dtos.AuthResponseDTO;
import com.socialproject.dtos.UserLoginRequestDTO;
import com.socialproject.dtos.UserRegisterRequestDTO;
import com.socialproject.entities.User;
import com.socialproject.security.JwtTokenProvider;
import com.socialproject.services.IUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl {

    @Autowired
    private AuthenticationManager authenticationManager; // Giriş kontrolcüsü

    @Autowired
    private JwtTokenProvider jwtTokenProvider; // Token basıcı

    @Autowired
    private IUserService userService; // Kullanıcı işlemleri

    @Autowired
    private PasswordEncoder passwordEncoder; // Şifre kırma makinesi (Hash)

    // --- 1. LOGIN (GİRİŞ) ---
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody UserLoginRequestDTO loginRequest) {
        
        // A) Kullanıcı adı ve şifreyi doğrula
        // Bu satır gidip UserDetailsServiceImpl'deki loadUserByUsername'i çalıştırır.
        // Şifre yanlışsa otomatik hata fırlatır.
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            )
        );

        // B) Doğrulandıysa içeri al (Context'e at)
        SecurityContextHolder.getContext().setAuthentication(auth);

        // C) Token Üret
        String jwtToken = "Bearer " + jwtTokenProvider.generateJwtToken(auth);

        // D) Cevabı hazırla ve dön
        User user = userService.getUserByUsername(loginRequest.getUsername()); // Bu metodu servise eklemelisin!
        
        AuthResponseDTO response = new AuthResponseDTO();
        response.setMessage("Giriş Başarılı!");
        response.setAccessToken(jwtToken);
        response.setUserId(user.getId());
        
        return response;
    }

    // --- 2. REGISTER (KAYIT) ---
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDTO registerRequest) {
        
        if(userService.getUserByUsername(registerRequest.getUsername()) != null) {
            return new ResponseEntity<>("Bu kullanıcı adı zaten alınmış!", HttpStatus.BAD_REQUEST);
        }

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        // --- 🛠️ ZORUNLU ALANLARI DOLDURMA (AUTO-FILL) ---
        
        // 1. Doğum Tarihi (Bugün)
        newUser.setBirthOfDate(java.time.LocalDate.now()); 
        
        // 2. Email (Kullanıcı adından uyduralım)
        newUser.setEmail(registerRequest.getUsername() + "@socialmedia.com");
        
        // 3. İsim ve Soyisim (Boş kalmasın diye "Anonim" yazalım)
        // Eğer veritabanında bunlar da zorunluysa hata alırsın, o yüzden dolduruyoruz.
        newUser.setFirstname("Anonim");
        newUser.setLastname("Kullanıcı");
        
        // ----------------------------------------------------
        
        userService.saveUser(newUser);

        return new ResponseEntity<>("Kullanıcı başarıyla kaydedildi!", HttpStatus.CREATED);
    }
}