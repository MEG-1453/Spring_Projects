package com.socialproject.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title", length = 150)
	private String title;
	
	@Column(name = "content", length = 5000)
	private String content;
	
	@CreationTimestamp
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
// --- İLİŞKİ KISMI (EN ÖNEMLİ YER) ---
    
    // Bir Post'un SADECE BİR sahibi (User) olabilir.
    // Veritabanında "posts" tablosuna "user_id" diye bir sütun açar.
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comment;
	
	@ManyToOne
	@JoinColumn(name = "repost_id", nullable = true)
	private Post originalPost;
	
	
	
	
	
	
	
	
}
