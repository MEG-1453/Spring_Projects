package com.socialproject.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialproject.controller.IUserController;
import com.socialproject.dtos.UserResponseDTO;
import com.socialproject.dtos.UserSaveRequestDTO;
import com.socialproject.dtos.UserUpdateRequestDTO;
import com.socialproject.entities.User;
import com.socialproject.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/users")
public class UserControllerImpl implements IUserController {
	
	// işlerin yürütüldüğü yer.
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@Override
	@PostMapping(path = "/save")
	public UserResponseDTO saveUser(@Valid @RequestBody UserSaveRequestDTO userSaveRequestDTO) { 
		//@RequestBody: "Gelen isteğin gövdesindeki (Body) JSON verisini al, User nesnesine dönüştür" der. @Valid, @email @size kontrol et der.
	
		// --- MAPPING (DTO -> ENTITY Dönüştürme) ---
        // Gelen güvenlikli DTO verisini, gerçek User nesnesine aktarıyoruz.
		
		User newUser = new User();
		
		newUser.setUsername(userSaveRequestDTO.getUsername());
		newUser.setFirstname(userSaveRequestDTO.getFirstname());
		newUser.setLastname(userSaveRequestDTO.getLastname());
		newUser.setBirthOfDate(userSaveRequestDTO.getBirthOfDate()); // LocalDate olduğu için sorunsuz geçer
		newUser.setEmail(userSaveRequestDTO.getEmail());
		newUser.setPassword(userSaveRequestDTO.getPassword());
		
		// 2. Kayıt İşlemi (Veritabanına gidiş geliş)
        // Service bize ID'si olan, veritabanına girmiş Entity'i geri verir.	
		User savedUser = userServiceImpl.saveUser(newUser);
	
	
		// 3. Entity -> DTO Çevrimi (Çıkış)
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		
		userResponseDTO.setFirstname(savedUser.getFirstname());
		userResponseDTO.setLastname(savedUser.getLastname());
		userResponseDTO.setBirthOfDate(savedUser.getBirthOfDate());
		userResponseDTO.setEmail(savedUser.getEmail());
		userResponseDTO.setId(savedUser.getId());
		userResponseDTO.setUsername(savedUser.getUsername());
		
		return userResponseDTO;
	
	
	
	}

	@Override
	@GetMapping("/get-all")
	public List<UserResponseDTO> getAllUsers() {
		
		// Direkt servisteki metodu çağırıyoruz.
        // Tüm çevirme işlemini Service yaptığı için burası çok temiz!
		return userServiceImpl.getAllUsers();
	}

	
	@Override
	@DeleteMapping(path = "/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		
		return userServiceImpl.deleteUser(id);
	}
    


	@Override
	@DeleteMapping("/delete-pro/{id}")
	public UserResponseDTO newDeleteUser(@PathVariable Long id) {
		return userServiceImpl.newDeleteUser(id);		
	}

	@Override
	@PutMapping("/update/{id}")
	public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserSaveRequestDTO userSaveRequestDTO) {
		
		return userServiceImpl.updateUser(id, userSaveRequestDTO);
		
	}

	@Override
	@PutMapping("/pro-update/{id}")
	public UserResponseDTO newUpdateUser(@PathVariable Long id, @RequestBody UserSaveRequestDTO updateRequest) {
		
		return userServiceImpl.newUpdateUser(id, updateRequest);
		
	}

}
