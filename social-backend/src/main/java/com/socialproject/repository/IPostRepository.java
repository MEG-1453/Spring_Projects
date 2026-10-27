package com.socialproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialproject.entities.Post;

public interface IPostRepository extends JpaRepository<Post, Long> {

	// Spring buna bakıp: "SELECT * FROM posts WHERE user_id = ?" sorgusunu kendi yazar.
    List<Post> findByUserId(Long userId);
}
