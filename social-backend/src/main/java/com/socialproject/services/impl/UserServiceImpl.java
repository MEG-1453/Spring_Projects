package com.socialproject.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;
import com.socialproject.dtos.UserResponseDTO;
import com.socialproject.dtos.UserSaveRequestDTO;
import com.socialproject.entities.User;
import com.socialproject.exception.UserNotFoundException;
import com.socialproject.repository.IPostRepository;
import com.socialproject.repository.IUserRepository;
import com.socialproject.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {


	// olaylar burda hazırlanır

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		
		// kullanıcı adı boş mu?
		if (user.getUsername() == null) {
			return null;
		}
		
		boolean isExists = userRepository.existsByUsername(user.getUsername());
		
		// kullanıcı eğer varsa kaydetme 
		if (isExists) {
			System.out.println("HATA: " + user.getUsername() + "Kullanıcı Adı Zaten Kayıtlı");
			return null;   // burala exception lar gelicek
		}
		
		//E-mail adresi zaten var mı? varsa kaydetme
		boolean isExistsEmail = userRepository.existsByEmail(user.getEmail());
		
		if (isExistsEmail) {
			System.out.println("HATA: Bu E-mail Adresi İle Daha Önce Kayıt Açılmıştır");
			return null;
		}
		
		// Eğer Her Şey sorunsuzza veri tabanına kaydediyoruz
		return userRepository.save(user);
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		
		// 1. Veritabanındaki HER ŞEYİ getir (Entity Listesi)
        // Select * From Users; sorgusu çalışır.
		List<User> userList = userRepository.findAll();
		
		// 2. Geri döndüreceğimiz BOŞ bir DTO listesi hazırladık.
		List<UserResponseDTO> responseList = new ArrayList<>();
		
		// 3. DÖNGÜ: Entityleri tek tek DTO'ya çevir ve listeye ekle.
        // "userList içindeki her bir 'user' için dön"
		for (User user : userList) {
			
			
			UserResponseDTO rDTO = new UserResponseDTO();
			
			// Entity'den al, DTO'ya koy (Mapping)
			rDTO.setId(user.getId());
			rDTO.setFirstname(user.getFirstname());
            rDTO.setLastname(user.getLastname());
            rDTO.setUsername(user.getUsername());
            rDTO.setEmail(user.getEmail());
            rDTO.setBirthOfDate(user.getBirthOfDate());
            
            // Hazır olan paketi, gönderilecekler kutusuna (Listeye) at
            responseList.add(rDTO);
		}
		
		// 4. Hazırladığımız DTO listesini döndür.
		return responseList;
	}

	
	@Override
	public String deleteUser(Long id) {
		
		// kullanıcı id si var mı kontorol
		if (!userRepository.existsById(id)) {
			/* ARTIK RETURN YOK! Hata fırlatıyoruz.
			 *Bu satır çalıştığı an kod kesilir, GlobalExceptionHandler devreye girer.
			
			return "kullanıcı bulunamadı";
			*/
			
			throw new UserNotFoundException("Silinecek kullanıcı bulunamadı! ID: " + id);
			
		}
		
		//silme işlermi
		userRepository.deleteById(id);
		
		//Sonuç
		return "Kullanıcı başarıyla silindi. ID: " + id;
		
	}
    

	@Override
	public UserResponseDTO newDeleteUser(Long id) {
		
		/*
		if (!userRepository.existsById(id)) {
			return null;
		}
		*/
		/* bu da eskidi
		User var = userRepository.findById(id).orElse(null);
		
		if (var == null) {
			return null;
		}
		*/
		
		
		// YENİ:
		User var = userRepository.findById(id)
		        .orElseThrow(() -> new UserNotFoundException("Silinecek kullanıcı bulunamadı. ID: " + id));
		
		
		
		UserResponseDTO responseDTO = new UserResponseDTO();
		
		//silinmeden veriler responseDTO kaydedildi without passwd
		responseDTO.setId(var.getId());
		responseDTO.setFirstname(var.getFirstname());
		responseDTO.setLastname(var.getLastname());
		responseDTO.setUsername(var.getUsername());
		responseDTO.setEmail(var.getEmail());
		responseDTO.setBirthOfDate(var.getBirthOfDate());
		
		//sileviliriz aerrık
		userRepository.deleteById(id);
		
		//sildiğimiz kişilerin bilgilerini dönüyoruz
		return responseDTO;
	}

	@Override														//updateRequest yazmak daha mantıklıydı :|
	public UserResponseDTO updateUser(Long id, UserSaveRequestDTO userSaveRequestDTO) {
		
		/* Artık buna gerek yok null dönmeyeceğiz try-catch yerine de aşağıdaki modern metodu kullanıcaz
		User var = userRepository.findById(id).orElse(null);
		
		if (var == null) {
			return null;
		}
		*/
		
		// YENİ PROFESYONEL HALİ:
		User var = userRepository.findById(id)
		        .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı. ID: " + id));
		
		// Artık if check yapmana gerek yok. 
		// Çünkü kullanıcı yoksa kod burada kesilir, GlobalExceptionHandler devreye girer.
		// Kodun kalanı çalışmaz.
		
		
		
		//kullanıcı verilerini önce çekiyoruz
		var.setFirstname(userSaveRequestDTO.getFirstname());
		var.setLastname(userSaveRequestDTO.getLastname());
		var.setUsername(userSaveRequestDTO.getUsername());
		var.setEmail(userSaveRequestDTO.getEmail());
		var.setBirthOfDate(userSaveRequestDTO.getBirthOfDate());
		var.setPassword(userSaveRequestDTO.getPassword());
		
		//şimdi de kaydediyoruz çektiklerimizi
		userRepository.save(var);
		
		
		//şimdi güncel halini json formatta dönüyoruz
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		
		userResponseDTO.setId(var.getId());
		userResponseDTO.setFirstname(var.getFirstname());
		userResponseDTO.setLastname(var.getLastname());
		userResponseDTO.setUsername(var.getUsername());
		userResponseDTO.setEmail(var.getEmail());
		userResponseDTO.setBirthOfDate(var.getBirthOfDate());
		
		return userResponseDTO;
	}

	
	//eğer kullanıcı sadece bir veri değiştirmek isterse bunu kullanıcaz
	@Override
	public UserResponseDTO newUpdateUser(Long id, UserSaveRequestDTO updateRequest)	 {
		
		/*
		User user = userRepository.findById(id).orElse(null);
		
		if (user == null) {
			return null;
		}
		*/
		
		// YENİ PROFESYONEL HALİ:
		User user = userRepository.findById(id)
		        .orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı. ID: " + id));
		
		// Artık if check yapmana gerek yok. 
		// Çünkü kullanıcı yoksa kod burada kesilir, GlobalExceptionHandler devreye girer.
		// Kodun kalanı çalışmaz.
		
		
		
		// kullanıcı null mı gönderdi kontrol
		if (updateRequest.getFirstname() != null && !updateRequest.getFirstname().isBlank()) {
			user.setFirstname(updateRequest.getFirstname());
		}
		
		if (updateRequest.getLastname() != null && !updateRequest.getLastname().isBlank()) {
			user.setLastname(updateRequest.getLastname());
		}
		
		if (updateRequest.getUsername() != null && !updateRequest.getUsername().isBlank()) {
			user.setUsername(updateRequest.getUsername());
		}
	
		if (updateRequest.getEmail() != null && !updateRequest.getEmail().isBlank()) {
			user.setEmail(updateRequest.getEmail());
		}
		
		if (updateRequest.getPassword() != null && !updateRequest.getPassword().isBlank()) {
			user.setPassword(updateRequest.getPassword());
		}
		
		if (updateRequest.getBirthOfDate() != null) {
			user.setBirthOfDate(updateRequest.getBirthOfDate());
		}
		
		//3. Veritabanına Kaydet (Hibernate ID olduğu için UPDATE atar)
		userRepository.save(user);
		
		//Güncel halini dto'ya çevirip dön
		return modelMapper(user);
	}
	
	
	//kod tekrarını önlemek için Halper method *normalde modelmapper metodu gerçekten var ama onda boşluk olup olmadığına bakmıyor
	private UserResponseDTO modelMapper(User user) {			//Not: biraz daha uzmanlaşınca da bu hazır metodlar üzerinde de oynıcaz
		
		UserResponseDTO response = new UserResponseDTO();
		
		response.setId(user.getId());
		response.setFirstname(user.getFirstname());
		response.setLastname(user.getLastname());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setBirthOfDate(user.getBirthOfDate());
		
		return response;
	}

	@Override
	public User getUserByUsername(String username) {
	    return userRepository.findByUsername(username); // Repository'den çağır
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
