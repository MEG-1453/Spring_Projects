package com.meg_1453.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meg_1453.entities.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {   // CrudRepository de extends edilebilirdi
	
	// buralara daha sonra kendi özel metotlarımızı yazacağız 

}
