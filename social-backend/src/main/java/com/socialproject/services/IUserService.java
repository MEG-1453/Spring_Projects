package com.socialproject.services;

import java.util.List;

import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;
import com.socialproject.dtos.UserResponseDTO;
import com.socialproject.dtos.UserSaveRequestDTO;
import com.socialproject.entities.User;

public interface IUserService {
	
	// ne isteniyorsa burda gösterilir
	
	
	User saveUser(User user);
	
	
	List<UserResponseDTO> getAllUsers();
	
	// kullanıcı silme
	String deleteUser(Long id);
	
	//kullanıcı silinir ama geri json formatta sildiğimiz kişinin verileri döner
	UserResponseDTO newDeleteUser(Long id);
	
	//kullanıcı güncelleme (Put işlemi)
	UserResponseDTO updateUser(Long id, UserSaveRequestDTO userSaveRequestDTO);
	
	UserResponseDTO newUpdateUser(Long id, UserSaveRequestDTO updateRequest);
	
	User getUserByUsername(String username);
	// void saveUser(User user); // Varsa gerek yok
	

}
