package com.meg_1453.controller;

import java.util.List;
import java.util.jar.Attributes.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meg_1453.model.Employee;
import com.meg_1453.model.UpdateEmployeeRequest;
import com.meg_1453.services.EmployeeService;

@RestController
@RequestMapping("/rest/api/employee")
public class RestEmployeeController {

	
	//Rest API Get istği GetMapping
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping(path = "/list")
	public List<Employee> getAllEmployeeList(){
		return employeeService.getAllEmplooyeeList();
	}
	
	
	//PathVariable
	@GetMapping(path = "/list/{id}")
	public Employee getEmployeeById(@PathVariable(name = "id" , required = true)String id) {
		return employeeService.getEmployeeById(id);
	}

	
	//RequestParam
	@GetMapping(path = "/with-params")   //employee-list-with-params
	public List<Employee> getEmployeeWithParams(@RequestParam(name = "firstName" , required = false) String firstName,
												@RequestParam(name = "lastName" , required = false) String lastName){
		
		return employeeService.getEmployeeWithParams(firstName, lastName) ;	
	}

	/*post gönderme işlemi PostMpping ile
	 * @RequestBody
	 */
	
	@PostMapping(path = "/save-employee")
	public Employee saveEmployee(@RequestBody Employee newEmployee) {
		
		return employeeService.savEmployee(newEmployee);
	}

	// delete isteği
	@DeleteMapping(path = "/delete-employee/{id}")
	public boolean deleteEmployee(@PathVariable(name = "id")String id) {
		return employeeService.deleteEmployee(id);
		
	}
	
	// update isteği
	@PutMapping(path = "/update-employee/{id}")
	public Employee updatEmployee(@PathVariable(name = "id") String id , @RequestBody UpdateEmployeeRequest request) {
		
		return employeeService.updatEmployee(id, request);
	}
	
	
	
	
	

	
	
	
	
}
