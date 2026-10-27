package com.socialproject.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserResponseDTO {

	// Geriye ID dönelim ki frontend bu kişiyi tanısın.
	private Long id;
	
	private String firstname;

	private String lastname;
	
	private String username;

	private String email;
	
	private LocalDate birthOfDate;
	
	//Password alanı YOK! Sildik. Güvenlik sağlandı.
}
