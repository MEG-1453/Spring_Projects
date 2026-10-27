package com.socialproject.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialproject.controller.ILikeController;
import com.socialproject.dtos.LikeSaveRequestDTO;
import com.socialproject.services.ILikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeControllerImpl implements ILikeController {

	@Autowired
	private ILikeService likeService;

	@Override
	@PostMapping("/toggle")
	public String toggleLike(@RequestBody LikeSaveRequestDTO request) {
		return likeService.toggleLike(request);
	}
	
	
}
