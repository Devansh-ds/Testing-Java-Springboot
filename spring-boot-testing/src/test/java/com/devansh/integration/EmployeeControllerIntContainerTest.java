package com.devansh.integration;

import com.devansh.model.Employee;
import com.devansh.repo.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntContainerTest extends AbstractionBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @DisplayName("Create Employee")
    @Test
    public void createEmployeeTest() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    @DisplayName("Get all employees")
    @Test
    public void getAllEmployeesTest() throws Exception {
        // given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Devansh").lastName("Singla").email("ds@gmail.com").build());
        employeeList.add(Employee.builder().firstName("Aman").lastName("Kumar").email("aman@gmail.com").build());
        employeeRepository.saveAll(employeeList);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees"));

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.size()", CoreMatchers.is(employeeList.size()))
                );
    }

    @DisplayName("Get emp by id +ve")
    @Test
    public void getEmployeeByIdTest() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/" + employee.getId())
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email", CoreMatchers.is(employee.getEmail()))
                );
    }

    @DisplayName("Get emp by id --ve")
    @Test
    public void getEmployeeByIdExceptionTest() throws Exception {
        // given
        // No employee is in the database

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees/" + 1));

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Update emp by id +ve")
    @Test
    public void updateEmployeeByIdTest() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        String newEmail = "new@gmail.com";
        savedEmployee.setEmail(newEmail);


        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/employees/" + savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedEmployee))
        );

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email", CoreMatchers.is(newEmail))
                );
    }

    @DisplayName("Update emp by id --ve")
    @Test
    public void updateEmployeeByIdExceptionTest() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();
        String newEmail = "new@gmail.com";
        // employeeRepository.save(employee);   we never saved it so it should give notFound Exception
        employee.setEmail(newEmail);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/employees/" + employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

        // then
        response
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("Delete emp by id ")
    @Test
    public void deleteEmployeeByIdTest() throws Exception {
        // given
        Employee employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/employees/" + savedEmployee.getId())
        );

        // then
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}