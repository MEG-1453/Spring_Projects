package com.socialproject.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponseDTO {

	private Long id;
	
	private String username;	// Yazarın Kullanıcı Adı (Frontend için lazım olur)
	
	private Long userId;   			//Yazarın Id'si
	
	private String title;
	
	private String content;
	
	private LocalDateTime createdDate;
	
	
	
	// Eğer bu bir repost ise, orijinal postun özetini de dönelim
	
	private Long originalPostId;
	private String originalPostTittle;
	private String originalPostUsername;
}
