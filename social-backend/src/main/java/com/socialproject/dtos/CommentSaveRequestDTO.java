package com.socialproject.dtos;

import lombok.Data;

@Data
public class CommentSaveRequestDTO {
	
	private String content;
	private Long userId;	// yazan kişi
	private Long postId;	// hedef kişi
	
}
