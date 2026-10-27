package com.socialproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.socialproject.entities.User;
import com.socialproject.repository.IUserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	
	@Autowired
	private IUserRepository userRepository;

	//SPRİNG SECURİTY BU METODU KENDİSİ OTOMATİK ÇAĞIRIR
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// KULLANICI VAR MI KONTROLÜ
		User user = userRepository.findByUsername(username); //DİŞKKAT!! REPOSİTORYE 'findByUsername' EKLEMEYİ UNUTMA!!!
		
		if (user == null) {
			throw new UsernameNotFoundException("Kullanıcı bulanamadı: " + username);
		}
		
		return JwtUserDetails.create(user);	
	}
	
	
	// --- ID İLE BULAMAMIZ GEREKİRSE DİYE HALPER METHOD YAZIYORUZ
	
	public UserDetails loadUserDetailsById(Long id) {
		
		User user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("Kullanıcı Bulunamadı ID: " + id));
		
		return JwtUserDetails.create(user);
		
	}

}
