package com.meg_1453.entities;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data                          // @Getter + @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Student")
public class Student {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name" , nullable = false , length = 50)
	private String firstName ;
	
	@Column(name = "last_name" , nullable = false , length = 50)
	private String lastName ;
	
	@DateTimeFormat(iso = ISO.DATE) // or @JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "birth_of_date" , nullable = true)
	private Date birtOfDate ;
	
}