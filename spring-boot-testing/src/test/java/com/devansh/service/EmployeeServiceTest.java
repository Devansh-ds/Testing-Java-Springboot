package com.devansh.service;

import com.devansh.exception.ResourceNotFoundException;
import com.devansh.model.Employee;
import com.devansh.repo.EmployeeRepository;
import com.devansh.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("d@gmail.com")
                .build();
    }

    @DisplayName("Save employee")
    @Test
    public void saveEmployeeTest() {
        // given
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);

        // then
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    @DisplayName("Save emp throws exception")
    @Test
    public void saveEmployeeExceptionTest() {
        // given
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when
        org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.saveEmployee(employee)
        );

        // then
        /*  BDDMockito.verify(...) → Checks if a method was called.
            employeeRepository → The mocked object you're checking.
            Mockito.never() → Verifies the method was not called at all.
            .save(...) → The method we expect to be skipped.
            BDDMockito.any(Employee.class) → Matches any Employee object
            (we're not verifying the exact object here, just that no Employee was passed to save()).
        */
        BDDMockito.verify(employeeRepository, Mockito.never())
                .save(BDDMockito.any(Employee.class));
    }

    @DisplayName("Get All emp +ve")
    @Test
    public void getAllEmployeeTest() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Aman")
                .lastName("Doe")
                .email("a@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll())
                .willReturn(List.of(employee1, employee1));

        // when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // then
        Assertions.assertThat(allEmployees).isNotNull();
        Assertions.assertThat(allEmployees.size()).isEqualTo(2);
    }

    @DisplayName("Get All emp --ve")
    @Test
    public void getAllEmployeeExceptionTest() {
        // given
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Aman")
                .lastName("Doe")
                .email("a@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        // when
        List<Employee> allEmployees = employeeService.getAllEmployees();

        // then
        Assertions.assertThat(allEmployees).isEmpty();
        Assertions.assertThat(allEmployees.size()).isEqualTo(0);
    }

    @DisplayName("Get emp by id +ve")
    @Test
    public void getEmployeeByIdTest() {
        // given
        BDDMockito.given(employeeRepository.findById(1L))
                .willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(1L);

        // then
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(1L);
    }

    @DisplayName("Get emp by id --ve")
    @Test
    public void getEmployeeByIdExceptionTest() {
        // given
        BDDMockito.given(employeeRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        org.junit.jupiter.api.Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(1L));

        // then (verify that repo was called only once)
        BDDMockito.verify(employeeRepository, Mockito.times(1)).findById(1L);
        BDDMockito.verifyNoMoreInteractions(employeeRepository);
    }

    @DisplayName("Update emp by id +ve")
    @Test
    public void updateEmployeeByIdTest() {
        // given
        BDDMockito.given(employeeRepository.findById(1L))
                .willReturn(Optional.of(employee));
        BDDMockito.given(employeeRepository.save(employee))
                .willReturn(employee);

        // when
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getId()).isEqualTo(1L);
    }

    @DisplayName("Update emp by id --ve")
    @Test
    public void updateEmployeeByIdExceptionTest () {
        // given
        BDDMockito.given(employeeRepository.findById(1L))
                .willReturn(Optional.empty());

        // when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(employee));

        // then
        BDDMockito.verify(employeeRepository, Mockito.never()).save(BDDMockito.any(Employee.class));
    }

    @DisplayName("Delete Emp by id")
    @Test
    public void deleteEmployeeByIdTest() {
        // given
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(1L);

        // when
        employeeService.deleteEmployee(1L);

        // then
        BDDMockito.verify(employeeRepository, Mockito.times(1)).deleteById(1L);
    }
}