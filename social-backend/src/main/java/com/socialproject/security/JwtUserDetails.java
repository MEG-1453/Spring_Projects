package com.socialproject.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.socialproject.entities.User;

import lombok.Data;

@Data
public class JwtUserDetails implements UserDetails{

	
	// BURASI SPRİNGİN GÜVENLİK İÇİN KULLANDIĞI ALAN
	
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities; // YETKİLER (ADMİN, USER ...)
	
	
	// USER NESNESİNİ USERDETAİLS'A DÖNÜŞTÜRME
	
	public JwtUserDetails(Long id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	
	// USER'I -> JWTUSERDETAİLS'E DÖNÜŞTÜME
	
	public static JwtUserDetails create(User user) {
		
		// DENEME AMAÇLI HERKESE "USER" YETKİSİ VERİYORUM
		List<GrantedAuthority> authoritiesList = new ArrayList<>();
		authoritiesList.add(new SimpleGrantedAuthority("user"));
		
		return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), authoritiesList);
	}
	
	// --- AŞAĞIDAKİLER ZORUNLU OVERRIDE METOTLARI ---
	
	@Override
    public boolean isAccountNonExpired() {
        return true; // HESABIN SÜRESİ DOLDU MU? HAYIR.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // HESAP KİLİTLİ Mİ? HAYIR.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Şifrenin süresi doldu mu? Hayır.
    }

    @Override
    public boolean isEnabled() {
        return true; // Hesap aktif mi? Evet.
    }
	
	
}

