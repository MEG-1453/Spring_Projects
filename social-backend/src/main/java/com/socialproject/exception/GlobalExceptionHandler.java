package com.socialproject.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice	//"Ben tüm Controller'ları dinleyen bir bekçiyim" demektir.
public class GlobalExceptionHandler {

	
	// 1. ÖZEL HATA YAKALAYICI
    // Eğer projede bir yerde "UserNotFoundException" fırlatılırsa, onu HAVADA YAKALA ve burayı çalıştır.
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),              // Şu anki zaman
                exception.getMessage(),           // Fırlatılan mesaj ("ID bulunamadı" vs.)
                webRequest.getDescription(false)  // Hangi URL'den geldi?  false yaptık ki frontend de kullanıcıların tüm verileri gitmesin.
        );											// bize sadece uri bilmek yeterli.
        
        // 404 NOT FOUND koduyla birlikte bizim şık kutuyu döndür.
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
	
	
	
	
	
	// 2. GENEL HATA YAKALAYICI (Güvenlik Ağı)
    // Yukarıdakiler dışında öngöremediğimiz bir hata olursa (NullPointer vs.) buraya düşsün.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
		
		ErrorDetails errorDetails = new ErrorDetails(
				
				LocalDateTime.now(),
				exception.getMessage(),
				webRequest.getDescription(false)
		);
		
		// 500 INTERNAL SERVER ERROR koduyla dön.
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
