package com.meg_1453.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meg_1453.model.Employee;
import com.meg_1453.model.UpdateEmployeeRequest;
import com.meg_1453.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository ;
	
	public List<Employee> getAllEmplooyeeList(){
	
		return employeeRepository.getAllEmployeeList(); 
	
	}
	
	public Employee getEmployeeById(String id) {
		return employeeRepository.getEmployeeById(id);
	}
	
	public List<Employee> getEmployeeWithParams(String firstName , String lastName){
		return employeeRepository.getEmployeeWithParams(firstName, lastName) ;
	}
	
	public Employee savEmployee(Employee newEmployee) {
		return employeeRepository.saveEmployee(newEmployee);
	}
	
	public boolean deleteEmployee(String id) {
		return employeeRepository.deleteEmployee(id);
	}
	
	public Employee updatEmployee(String id , UpdateEmployeeRequest request) {
		return employeeRepository.updatEmployee(id, request);
	}
	
}
