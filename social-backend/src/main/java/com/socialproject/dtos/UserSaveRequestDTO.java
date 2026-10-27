package com.socialproject.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSaveRequestDTO {

	// ID YOK! (Çünkü ID'yi veritabanı verir, kullanıcı veremez)
	// CREATED_AT YOK! (Otomatik oluşur)

	@NotBlank(message = "Ad alanı boş bırakılamaz")
	private String firstname;

	@NotBlank(message = "Soyad kısmı boş bırakılamaz")
	private String lastname;

	@NotBlank(message = "Kullanıcı adı zorunludur")
	@Size(min = 5, max = 50, message = "Kullanıcı adı 3-50 karakter arasında olmalı")
	private String username;

	@Email(message = "Geçerli bir E-mail giriniz")
	@NotBlank(message = "E-Mail zorunludur")
	private String email;

	@NotBlank(message = "Şifre Zorunludur")
	@Size(min = 6, message = "Şifreniz minimum 6 karakter olmalıdır")
	private String password;

	@NotNull(message = "Doğum Tarihi Boş Olamaz")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate birthOfDate;

}
