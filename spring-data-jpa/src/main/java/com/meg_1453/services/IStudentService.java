package com.meg_1453.services;

import com.meg_1453.entities.Student;
import java.util.List;

public interface IStudentService {

	public Student saveStudent(Student student);
	
	public List<Student> getAllStudents();
	
	public Student getStudentById(Integer id);
	
	public void deleteStudent(Integer id);
	
	public Student updateStudent(Integer id , Student updateStudent);
}
