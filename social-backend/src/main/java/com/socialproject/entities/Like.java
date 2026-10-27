package com.socialproject.entities;

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
@Table(name = "likes") //like yapsaydık veri tabanı patlardı özel type
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//kim beğendi?
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	//neyi beğendi
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

}
