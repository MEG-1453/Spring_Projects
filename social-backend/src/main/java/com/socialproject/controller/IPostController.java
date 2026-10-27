package com.socialproject.controller;

import java.util.List;

import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;

public interface IPostController {
	PostResponseDTO createPost(PostSaveRequestDTO postSaveRequestDTO);
	
	List<PostResponseDTO> getAllPosts();
	
	List<PostResponseDTO> getPostsByUserId(Long userId);
}
