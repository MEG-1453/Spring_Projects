package com.socialproject.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialproject.dtos.CommentResponseDTO;
import com.socialproject.dtos.CommentSaveRequestDTO;
import com.socialproject.entities.Comment;
import com.socialproject.entities.Post;
import com.socialproject.entities.User;
import com.socialproject.exception.UserNotFoundException;
import com.socialproject.repository.ICommentRepository;
import com.socialproject.repository.IPostRepository;
import com.socialproject.repository.IUserRepository;
import com.socialproject.services.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService{

	// comment edebilmek için bu 3 repository bağlamak gerekiyor
	
	
	@Autowired
	private ICommentRepository commentRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IPostRepository postRepository;
	
	
	@Override
	public CommentResponseDTO createComment(CommentSaveRequestDTO requestDTO) {
		
		// 1. Kullanıcı Var mı?
		User user = userRepository.findById(requestDTO.getUserId())	    //istersek PostNotFoundException metodu yazıp da kullanabilirdik.
				.orElseThrow(() -> new UserNotFoundException("Kullanıcı bulunamadı ID: " + requestDTO.getUserId()));
		
		// 2. Post Var mı? (Hata sınıfını UserNotFound kullandım şimdilik, ileride PostNotFound yaparsın)
		Post post = postRepository.findById(requestDTO.getPostId())
				.orElseThrow(() -> new UserNotFoundException("Gönderi (Post) bulunamadı ID: " + requestDTO.getPostId()));
		
		//Yorum oluşturma ve İlişki kurma
		Comment comment = new Comment(); 
		
		
		comment.setContent(requestDTO.getContent());
		
		comment.setUser(user); // Yazarı atıyoruz
		comment.setPost(post); // Hedefi atıyoruz
		
		// Kaydet
		Comment savedComment = commentRepository.save(comment);
		
		//DTO'ya çeviriyoruz
		CommentResponseDTO response = new CommentResponseDTO();
		
		response.setId(savedComment.getId());
		response.setContent(savedComment.getContent());
		response.setCreatedDate(savedComment.getCreatedDate());
		response.setPostId(post.getId());
		response.setUserId(user.getId());
		response.setUsername(user.getUsername());
		
		return response;
	}


	@Override
	public List<CommentResponseDTO> getAllCommentsByPost(Long postId) {
		
		// 1. Veritabanından o postun yorumlarını çek
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		return mapToDTOList(comments);
		
	}
	
	// halper method:
	private List<CommentResponseDTO> mapToDTOList(List<Comment> comments){
		
		List<CommentResponseDTO> responseList = new ArrayList<>();
	
		for (Comment comment : comments) {
			CommentResponseDTO dto = new CommentResponseDTO();
			
			dto.setId(comment.getId());
			dto.setContent(comment.getContent());
			dto.setCreatedDate(comment.getCreatedDate());
			
			//yazan kişinin bilgisi
			dto.setUserId(comment.getUser().getId());
			dto.setUsername(comment.getUser().getUsername());
			
			// Hangi post olduğunu söyle
			dto.setPostId(comment.getPost().getId());
			
			responseList.add(dto);
		}
		return responseList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
