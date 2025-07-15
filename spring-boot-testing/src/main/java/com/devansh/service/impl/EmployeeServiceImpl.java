package com.devansh.service.impl;

import com.devansh.exception.ResourceNotFoundException;
import com.devansh.model.Employee;
import com.devansh.repo.EmployeeRepository;
import com.devansh.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent()) {
            throw new ResourceNotFoundException("Employee with email " + employee.getEmail() + " already exists");
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new ResourceNotFoundException("Employee with id " + id + " not found");
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> oldEmployee = employeeRepository.findById(employee.getId());
        if (oldEmployee.isPresent()) {
            return employeeRepository.save(employee);
        }
        throw new ResourceNotFoundException("Employee with id " + employee.getId() + " not found");
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
