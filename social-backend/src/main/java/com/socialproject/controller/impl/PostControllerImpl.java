package com.socialproject.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialproject.controller.IPostController;
import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;
import com.socialproject.services.IPostService;

@RestController
@RequestMapping("/api/posts")
public class PostControllerImpl implements IPostController {
	
	@Autowired
	private IPostService postService;

	@Override
	@PostMapping("/create")
	public PostResponseDTO createPost(@RequestBody PostSaveRequestDTO postSaveRequestDTO) {
		
		return postService.createPost(postSaveRequestDTO);
	}

	@Override
	@GetMapping("/get-all")
	public List<PostResponseDTO> getAllPosts() {
		// TODO Auto-generated method stub
		return postService.getAllPosts();
	}

	@Override
	@GetMapping("/get-by-user/{userId}")
	public List<PostResponseDTO> getPostsByUserId(@PathVariable(name = "userId") Long userId) {
		// TODO Auto-generated method stub
		return postService.getPostsByUserId(userId);
	}

}
