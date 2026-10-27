package com.socialproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialproject.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{

	
	// JPA kullanarak burda tüm gerekli işlemleri veri tabanına yaptırmış oluyoruz 
	// ve bu sayede veri tabanı normalden daha hızlı çalışarak eliyle koymuş gibi bulur
	// aynı şekilde eğer findAll(); kullansaydık tüm veriyi toplayıp bu o kullanıcı mı diye sürekli sorgu yapıcaktı ki
	// bu durum ram'i çok büyük ölçüde zorlar bırak veri tabanı senin yerine her şeyi yapsın.
	//repository bir çevirmendir aslında : Repository -> PostgreSQL 
	
	
	// kullanıcı adına göre bulma
	User findByUsername(String username);   
	
	
	// kullanıcı adı var mı? isUserName
	boolean existsByUsername(String username);
	
	// E-mail var mı?
	boolean existsByEmail(String email);
	
	// GÜVENLİK İÇİN USER METODU AMA DAHA ÖNCE ZATEN YAZMIŞIM
	//User findByUsername(String username);
	
	
	/*
	// SENİN GÖRMEDİĞİN, SPRING'IN RAM'DE OLUŞTURDUĞU HAYALET SINIF
	public class SimpleJpaRepository implements IUserRepository {
    @Override
    public User findByUsername(String username) {
        // Spring buraya otomatik SQL kodunu döşer
        String sql = "SELECT * FROM users WHERE username = ?";
        // ... veritabanına git, sonucu al, User nesnesine çevir ...
        return user;
    }
}

		A. findByUsername Analizi
	
	find: Spring bunu görünce "SELECT * FROM" yazar.
	By: Bunu görünce "WHERE" yazar.
	Username: Bunu görünce Entity dosyasındaki (User.java) username değişkenine bakar. "username = ?" yazar.
	Sonuç SQL: SELECT * FROM users WHERE username = 'Muhammet'
	
	----------------------------------------------------------------------------------------------------------------------
	
		B. existsByUsername Analizi
	
	exists: Spring bunu görünce SQL'i optimize eder, tüm veriyi çekmez, sadece "Varlığını kontrol et (Count > 0)" der.
	By: "WHERE"
	Username: "username = ?"
	Sonuç SQL: SELECT count(*) FROM users WHERE username = 'Muhammet'

	 */
	
	
}
