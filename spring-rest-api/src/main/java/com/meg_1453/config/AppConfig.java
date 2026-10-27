package com.meg_1453.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meg_1453.model.Employee;

@Configuration
public class AppConfig {
	
	@Bean
	public List<Employee> employeeList(){
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee("1", "Muhammed", "Emin"));
		employeeList.add(new Employee("2", "Salih", "Emir"));
		employeeList.add(new Employee("3", "M", "Emin"));
		employeeList.add(new Employee("4", "Mu", "Emin"));
		employeeList.add(new Employee("5", "Muh", "Emin"));
		employeeList.add(new Employee("6", "Muha", "Emin"));
		employeeList.add(new Employee("7", "Muham", "Emin"));
		employeeList.add(new Employee("8", "Muhamm", "Emin"));
		employeeList.add(new Employee("9", "Muhamme", "Emin"));
		employeeList.add(new Employee("10","Muhammed", "Emin"));
		
		return employeeList;
		
	}

}
