package com.socialproject.services;

import java.util.List;

import com.socialproject.dtos.CommentResponseDTO;
import com.socialproject.dtos.CommentSaveRequestDTO;

public interface ICommentService {

	CommentResponseDTO createComment(CommentSaveRequestDTO requestDTO);
	
	List<CommentResponseDTO> getAllCommentsByPost(Long postId);
	
}
