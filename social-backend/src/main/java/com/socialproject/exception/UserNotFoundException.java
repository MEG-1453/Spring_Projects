package com.socialproject.exception;


//RuntimeException ile extend(miras bırakıp) özelleştirme yapıyoruz.
public class UserNotFoundException extends RuntimeException{
	
	// constructer: hata fırlatmak için.
	public UserNotFoundException(String message) {
		super(message);
	}
	

}
