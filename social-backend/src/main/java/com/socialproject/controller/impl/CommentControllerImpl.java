package com.socialproject.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialproject.controller.ICommentController;
import com.socialproject.dtos.CommentResponseDTO;
import com.socialproject.dtos.CommentSaveRequestDTO;
import com.socialproject.services.ICommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentControllerImpl implements ICommentController {

	@Autowired
	private ICommentService commentService;

	@Override
	@PostMapping("/save")
	public CommentResponseDTO createComment(@RequestBody CommentSaveRequestDTO requestDTO) {
		return commentService.createComment(requestDTO);
	}

	@Override
	@GetMapping("/get-comments-by-post/{postId}")
	public List<CommentResponseDTO> getAllCommentsByPost(@PathVariable(name = "postId") Long postId) {
		return commentService.getAllCommentsByPost(postId);
	}
	
	
	
	
	
}
