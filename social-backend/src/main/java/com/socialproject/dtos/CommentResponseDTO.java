package com.socialproject.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponseDTO {

	private Long id;
	private String content;
	private LocalDateTime createdDate;
	
	//yazar bilgisi
	private Long userId;
	private String username;
	
	//post bilgisi (hangi posta yazıyor) yazmak zorunda değiliz hangi posta yazdığını görmek istersek
	private Long postId;
	
	
}
