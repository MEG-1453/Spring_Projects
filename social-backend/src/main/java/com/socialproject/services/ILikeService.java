package com.socialproject.services;

import com.socialproject.dtos.LikeSaveRequestDTO;

public interface ILikeService {
	
	String toggleLike(LikeSaveRequestDTO request);

}
