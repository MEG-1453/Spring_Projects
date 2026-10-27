package com.socialproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialproject.entities.Like;

public interface ILikeRepository extends JpaRepository<Like, Long> {

	// User ID ve Post ID'ye göre beğeni var mı diye kontrol eden sihirli metot
    // Optional kullanıyoruz çünkü sonuç boş (null) da dönebilir.
	
	Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
}
