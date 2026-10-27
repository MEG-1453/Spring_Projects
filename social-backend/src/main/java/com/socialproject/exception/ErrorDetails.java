package com.socialproject.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	private LocalDateTime timestamp; //hata ne zaman oldu gün-saat format
	private String message; 		 //Hata ne?
	private String details;			 //Hangi URL'de oldu 	
}
