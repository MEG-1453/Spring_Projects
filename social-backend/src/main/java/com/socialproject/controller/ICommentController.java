package com.socialproject.controller;

import java.util.List;

import com.socialproject.dtos.CommentResponseDTO;
import com.socialproject.dtos.CommentSaveRequestDTO;

public interface ICommentController {

	CommentResponseDTO createComment(CommentSaveRequestDTO requestDTO);
	
	List<CommentResponseDTO> getAllCommentsByPost(Long postId);
}
