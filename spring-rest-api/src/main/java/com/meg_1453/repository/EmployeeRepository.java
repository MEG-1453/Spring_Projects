package com.meg_1453.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.meg_1453.config.AppConfig;
import com.meg_1453.model.Employee;
import com.meg_1453.model.UpdateEmployeeRequest;

import lombok.Setter;

@Repository
public class EmployeeRepository {

    @Autowired
	private List<Employee> employeeList;

    EmployeeRepository(AppConfig appConfig) {
    }

	public List<Employee> getAllEmployeeList() {

		return employeeList;
	}

	public Employee getEmployeeById(String id) {
		Employee findEmployee = null;
		for (Employee employee : employeeList) {
			if (id.equals(employee.getId())) {
				findEmployee = employee;
				break;
			}

		}
		return findEmployee;
	}

	public List<Employee> getEmployeeWithParams(String firstName, String lastName) {
		List<Employee> employeeWithParams = new ArrayList<>();
		if (firstName == null && lastName == null) {
			return employeeList;
		}
		for (Employee employee : employeeList) {
			if (firstName != null && lastName != null) {
				if (employee.getFirstName().equalsIgnoreCase(firstName)
						&& employee.getLastName().equalsIgnoreCase(lastName)) {
					employeeWithParams.add(employee);
				}
			}
			if (firstName != null && lastName == null) {
				if (employee.getFirstName().equalsIgnoreCase(firstName)) {
					employeeWithParams.add(employee);
				}
			}
			if (lastName != null && firstName == null) {
				if (employee.getLastName().equalsIgnoreCase(lastName)) {
					employeeWithParams.add(employee);
				}
			}
		}
		return employeeWithParams;
	}
	
	public Employee saveEmployee(Employee newEmployee) {
		employeeList.add(newEmployee);
		return newEmployee;
	}
	
	
	public boolean deleteEmployee(String id) {
		//eğer veri tabanı bilseydim yapacağım şey :
		//delete from Employee WHERE id = :id
		Employee deleteEmployee = null ;
		for (Employee employee : employeeList) {
			if (id.equals(employee.getId())) {
				deleteEmployee = employee;
				break;
			}
		}
		if (deleteEmployee == null) {
			return false;
		}
		employeeList.remove(deleteEmployee);
		return true;				
	}
	
	private Employee findEmployeeById(String id) {
		Employee findEmployee = null ;
		for (Employee employee : employeeList) {
			if (employee.getId().equals(id)) {
				findEmployee = employee;
			}
		}
		return findEmployee;
	}
	
	public Employee updatEmployee(String id , UpdateEmployeeRequest request) {
		Employee findEmployee = findEmployeeById(id);
		if (findEmployee != null) {
			deleteEmployee(id);
			
			Employee updatedEmployee = new Employee();
			updatedEmployee.setId(id);
			updatedEmployee.setFirstName(request.getFirstName());
			updatedEmployee.setLastName(request.getLastName());
			
			employeeList.add(updatedEmployee);
			
			return updatedEmployee;
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
