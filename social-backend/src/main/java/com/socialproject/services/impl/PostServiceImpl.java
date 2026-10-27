package com.socialproject.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialproject.dtos.PostResponseDTO;
import com.socialproject.dtos.PostSaveRequestDTO;
import com.socialproject.entities.Post;
import com.socialproject.entities.User;
import com.socialproject.exception.UserNotFoundException;
import com.socialproject.repository.IPostRepository;
import com.socialproject.repository.IUserRepository;
import com.socialproject.services.IPostService;

@Service
public class PostServiceImpl implements IPostService {
	
	@Autowired		// KULLANICIYI BULMAK İÇİN BUNA İHTİYACIMIZ VAR
	private IUserRepository userRepository;	
	
	@Autowired
	private IPostRepository postRepository;

	@Override
	public PostResponseDTO createPost(PostSaveRequestDTO postSaveRequestDTO) {
		
		User user = userRepository.findById(postSaveRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Kullanıcı Bulunamadı! ID: " + postSaveRequestDTO.getUserId()));
		
		// 2. ADIM: Post Entity OLUŞTUR VE DOLDUR
        Post newPost = new Post();
        newPost.setTitle(postSaveRequestDTO.getTittle());
        newPost.setContent(postSaveRequestDTO.getContent());
        
        // --- İLİŞKİYİ KURDUĞUMUZ YER ---
        newPost.setUser(user); // "BU POSTUN SAHİBİ YUKARIDA BULDUĞUM user'dır" DEDİK.

        
        
        //------------- YENİ EKLENDİ (REPOST)----------------
        
        if (postSaveRequestDTO.getRepostId() != null) {
			
        	//REPOST EDİLMEK İSTENEN ORİJİNAL POSTU BUL
        	Post originalPost = postRepository.findById(postSaveRequestDTO.getRepostId())
        			.orElseThrow(() -> new UserNotFoundException("REPOST EDİLEN GÖNDERİ BULUNAMADI"));
        	
        	// YENİ POSTUN İÇİNE ORİJİNALİNİ GÖM
        	newPost.setOriginalPost(originalPost);
		}
        
        //---------------------------------------------------
        
        
        
        // 3. ADIM: KAYDET
        Post savedPost = postRepository.save(newPost);
        
        // 4. ADIM: CEVAP DÖN (DTO Mapping)
        PostResponseDTO response = new PostResponseDTO();
        response.setId(savedPost.getId());
        response.setTitle(savedPost.getTitle());
        response.setContent(savedPost.getContent());						// BUNU YAZMAMAIZA BİLE GEREK YOK ASLINDA MAPTODOLİST METODU
        response.setCreatedDate(savedPost.getCreatedDate());				// BENİM YERİME YAPIYOR ZATEN
        
        // YAZAR BELGELERİ DE EKLENDİ
        response.setUserId(savedPost.getUser().getId());
        response.setUsername(savedPost.getUser().getUsername());
        
        // Repost VERİLERİNİ DOLDUR
        if (savedPost.getOriginalPost() != null) {
			response.setOriginalPostId(savedPost.getOriginalPost().getId());
			response.setOriginalPostTittle(savedPost.getOriginalPost().getTitle());
			response.setOriginalPostUsername(savedPost.getOriginalPost().getUser().getUsername());
		}
        
        return response;
	}

	@Override
	public List<PostResponseDTO> getAllPosts() {
		List<Post> postList = postRepository.findAll(); 
		
		return mapToDTOList(postList); // AŞAĞIDAKİ YARDIMCI METODA YOLLUYORUZ
	}

	@Override
	public List<PostResponseDTO> getPostsByUserId(Long userId) {
		
		// 1. Önce böyle bir kullanıcı var mı kontrol et (Opsiyonel ama iyi olur)
	    // Şimdilik direkt repository'den çekiyoruz
	    List<Post> postList = postRepository.findByUserId(userId);
	    
	    return mapToDTOList(postList);
	}
	
	//bu çok önemli benim yerime tümünü map(dönüştür / eşle) dto listesine
	private List<PostResponseDTO> mapToDTOList(List<Post> posts) {
		
		List<PostResponseDTO> responseList = new ArrayList<>();	//En son tüm veriler bu listeye eklenir ve return edilir.
		
		for (Post post : posts) {
			PostResponseDTO dto = new PostResponseDTO();
			
			dto.setId(post.getId());
			dto.setTitle(post.getTitle());
	        dto.setContent(post.getContent());
	        dto.setCreatedDate(post.getCreatedDate());
			
	        // İlişkiden gelen veriler:
	        dto.setUserId(post.getUser().getId());
	        dto.setUsername(post.getUser().getUsername()); // <-- BURASI ÇOK HAVALI!
	        
	        // Repost verilerini doldur
	        if (post.getOriginalPost() != null) {
	            dto.setOriginalPostId(post.getOriginalPost().getId());
	            dto.setOriginalPostTittle(post.getOriginalPost().getTitle());
	            dto.setOriginalPostUsername(post.getOriginalPost().getUser().getUsername());
	        }
	        
	        responseList.add(dto);
		}
		return responseList;
	}
	
	
	
	
	
	
	
	
	

}
