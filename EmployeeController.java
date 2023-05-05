package com.edubridge.springboot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edubridge.springboot.exception.ResourceNotFoundException;

import com.edubridge.springboot.entity.Employee;
import com.edubridge.springboot.repository.EmployeeRepository;



@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	// Get all Employees
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public List<Employee> getAllEmployee(){
		return this.employeeRepository.findAll();
	}
	
	// Get employees by id
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable (value= "id") long employeeId) {
		return this.employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
				
	}
	
	// create employee
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return this.employeeRepository.save(employee);
	}
	
	// update employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@RequestBody Employee employee, @PathVariable ("id") long employeeId) {
		Employee existingEmployee =	 this.employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeId));
			existingEmployee.setFirstName(employee.getFirstName());
			existingEmployee.setLastName(employee.getLastName());
			existingEmployee.setEmail(employee.getEmail());
			return this.employeeRepository.save(existingEmployee);

		} 
	
	// delete employee by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteUser(@PathVariable (value = "id") long employeeId){
		Employee existingEmployee =	 this.employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + employeeId));
		this.employeeRepository.delete(existingEmployee);
		return ResponseEntity.ok().build();
	}

}