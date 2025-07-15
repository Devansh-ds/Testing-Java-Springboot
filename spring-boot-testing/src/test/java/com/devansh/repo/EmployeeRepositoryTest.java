package com.devansh.repo;

import com.devansh.model.Employee;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .build();
    }

    @DisplayName("JUnit test for saving employee")
    @Test
    public void saveEmployeeTest() {

        // given - setup or initial value

        // when - behaviour we want to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Get all employees")
    @Test
    public void getAllEmployeesTest() {
        // given
        for (int i = 0; i < 5; i++) {
            Employee employee = Employee
                    .builder()
                    .firstName("f: " + i)
                    .lastName("l: " + i)
                    .email(i + "@gmail.com")
                    .build();
            employeeRepository.save(employee);
        }

        // when
        List<Employee> employees = employeeRepository.findAll();

        // then
        assertThat(employees).isNotNull();
    }

    @DisplayName("Get employee by id")
    @Test
    public void getByIdTest() {
        // given
        employeeRepository.save(employee);

        // when
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then
        assertThat(employeeOptional.isPresent()).isTrue();
    }

    @DisplayName("Find by email")
    @Test
    public void jpaCustomQueryTest() {
        // given
        employeeRepository.save(employee);

        // when
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());

        // then
        assertThat(employeeOptional.isPresent()).isTrue();
    }

    @Test
    public void updateEmployeeTest() {
        // given
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.setEmail("other@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("other@gmail.com");
    }

    @DisplayName("Delete employee by id")
    @Test
    public void deleteEmployeeTest() {
        // given
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.save(employee);
        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(savedEmployee.getId());

        // then
        assertThat(employeeOptional.isPresent()).isFalse();
    }

    @DisplayName("Custom query using JPQL with index")
    @Test
    public void jpqlTest() {
        // given
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByJPQL("John", "Doe");

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Doe");
    }

    @DisplayName("Custom Query JPQL using named parameter")
    @Test
    public void jpqlNamedParameterTest() {
        // given
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findByJPQLNamedParmas("John", "Doe");

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Doe");
    }


}








