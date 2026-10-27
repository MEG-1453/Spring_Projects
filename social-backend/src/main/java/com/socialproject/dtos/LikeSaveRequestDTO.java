package com.socialproject.dtos;

import lombok.Data;

@Data
public class LikeSaveRequestDTO {

	private Long userId;
	private Long postId;
}
