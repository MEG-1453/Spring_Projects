package com.socialproject.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstname;

	@Column(name = "last_name", length = 50, nullable = false)
	private String lastname;

	@JsonFormat(pattern = "dd-MM-yyyy")  			//@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "birth_of_date", nullable = false)
	private LocalDate birthOfDate;
	
	@Column(name = "user_name", length = 50, nullable = false, unique = true)
	private String username;
	
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "password", length = 64, nullable = false)
	private String password;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	// Bir Kullanıcının ÇOK Postu olabilir.
    // mappedBy = "user" -> "Git Post sınıfındaki 'user' değişkenine bak, sahibi o."
    // cascade = ALL -> Kullanıcıyı silersem, onun postlarını da sil. (Yetim kalmasınlar)
    // fetch = LAZY -> Kullanıcıyı çektiğimde postları hemen getirme, ben istersem getir (Performans için).
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comment;

	
}
