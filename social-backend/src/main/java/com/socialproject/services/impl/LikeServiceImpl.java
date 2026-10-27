package com.socialproject.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialproject.dtos.LikeSaveRequestDTO;
import com.socialproject.entities.Like;
import com.socialproject.entities.Post;
import com.socialproject.entities.User;
import com.socialproject.exception.UserNotFoundException;
import com.socialproject.repository.ILikeRepository;
import com.socialproject.repository.IPostRepository;
import com.socialproject.repository.IUserRepository;
import com.socialproject.services.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService{

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IPostRepository postRepository;
	
	@Autowired
	private ILikeRepository likeRepository;

	@Override
	public String toggleLike(LikeSaveRequestDTO request) {
		
		//user ve post (opsiyonel) id kontrolü
		
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserNotFoundException("Kullanıcı ID'si Bulunamadı"));
		
		Post post = postRepository.findById(request.getPostId())
				.orElseThrow(() -> new UserNotFoundException("Post ID Bulunamadı"));
		
		// Daha önce beğenildi mi?
		Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(request.getUserId(), request.getPostId());
		
		if (existingLike.isPresent()) {

			//Zaten beğenmiş, O zaman SİL (Unlike)
			likeRepository.delete(existingLike.get());
			
			return "Beğeni Geri Alındı (Unlike)";
		}else {
			//Beğenmemiş O zaman EKLE (Like)
			Like newLike = new Like();
			
			newLike.setUser(user);
			newLike.setPost(post);
			
			likeRepository.save(newLike);
			
			return "Beğeni Başarıyla eklendi (Like)";
		}
		
		
	}
	
	
	
}
