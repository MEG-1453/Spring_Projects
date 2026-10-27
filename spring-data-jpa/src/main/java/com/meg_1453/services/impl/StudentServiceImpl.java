package com.meg_1453.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.meg_1453.controller.impl.StudentControllerImpl;
import com.meg_1453.entities.Student;
import com.meg_1453.repository.IStudentRepository;
import com.meg_1453.services.IStudentService;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	private IStudentRepository studentRepository;

	
	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = studentRepository.findAll();
		return studentList;
	}

	@Override
	public Student getStudentById(Integer id) {
		Optional<Student> optional = studentRepository.findById(id);
		if(!optional.isEmpty() || optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void deleteStudent(Integer id) {
		Student dbStudent = getStudentById(id);
		if(dbStudent != null) {
			studentRepository.delete(dbStudent);			
		}
	}

	@Override
	public Student updateStudent(Integer id, Student updateStudent) {
		Student dbStudent = getStudentById(id);
		if(dbStudent != null) {
			dbStudent.setFirstName(updateStudent.getFirstName());
			dbStudent.setLastName(updateStudent.getLastName());
			dbStudent.setBirtOfDate(updateStudent.getBirtOfDate());
			
			studentRepository.save(dbStudent);
		}
		return null;
	}

}
