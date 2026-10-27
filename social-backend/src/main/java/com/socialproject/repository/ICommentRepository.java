package com.socialproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialproject.entities.Comment;


public interface ICommentRepository extends JpaRepository<Comment, Long>{
	
	// İleride "Posta göre yorum getir" diyeceğiz, şimdilik boş kalsın.
	
	
	// SİHİRLİ SATIR: "Bana şu post ID'ye ait tüm yorumları bul"
	List<Comment> findByPostId(Long postId);
	
	
}
