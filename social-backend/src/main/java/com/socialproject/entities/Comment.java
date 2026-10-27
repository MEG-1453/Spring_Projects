package com.socialproject.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", length = 500)
	private String content;
	
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	
	
	// ---1. Ilişki (requst) Yorumun Sahibi ---
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	// ---2. Ilişki (request) Yorumun yapıldığı post---
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	
}
