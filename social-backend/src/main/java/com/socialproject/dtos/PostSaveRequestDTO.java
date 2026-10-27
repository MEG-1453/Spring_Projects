package com.socialproject.dtos;

import lombok.Data;

@Data
public class PostSaveRequestDTO {
	
	
	private String tittle;

	private String content;
	
	private Long userId;      // burası önemli gönderiyi kim attığını belirler
	
	// repost için id
	private Long repostId;
	
}
