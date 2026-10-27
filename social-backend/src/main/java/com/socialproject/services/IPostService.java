package com.socialproject.services;

import java.util.List;

import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;

public interface IPostService {
	
	//post işlemi
	PostResponseDTO createPost(PostSaveRequestDTO postSaveRequestDTO);

	List<PostResponseDTO> getAllPosts();
	List<PostResponseDTO> getPostsByUserId(Long userId);
}
