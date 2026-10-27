package com.socialproject.dtos;

import java.time.LocalDate;

import lombok.Data;



//Bu dto, temiz ayar yapmak için açılmış ve bu sayede hangi verinin
// değişeceğine sen karar vermiş olursun kolaylıkla


@Data
public class UserUpdateRequestDTO {

	private String firstname;
	
	private String lastname;
	
	private String username;
	
	private String email;
	
	private String password;
	
	private LocalDate birtOfDate;
}
