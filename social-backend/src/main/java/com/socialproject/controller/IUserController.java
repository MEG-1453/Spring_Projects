package com.socialproject.controller;

import java.util.List;

import com.socialproject.dtos.UserResponseDTO;
import com.socialproject.dtos.UserSaveRequestDTO;
import com.socialproject.dtos.UserUpdateRequestDTO;
import com.socialproject.entities.User;

public interface IUserController {

	// nelerin yürütüldüğünü söyler
	
	// Parametre değişti: User -> UserSaveRequestDTO
	UserResponseDTO saveUser(UserSaveRequestDTO userSaveRequestDTO);
	
	List<UserResponseDTO> getAllUsers();
	
	String deleteUser(Long id);
	
	UserResponseDTO newDeleteUser(Long id);
	
	UserResponseDTO updateUser(Long id, UserSaveRequestDTO userSaveRequestDTO);
	
	UserResponseDTO newUpdateUser(Long id, UserSaveRequestDTO updateRequest);
}
