package com.devansh.controller;

import com.devansh.model.Employee;
import com.devansh.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeControllerTest.MockedServiceConfig.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @TestConfiguration
    static class MockedServiceConfig {
        @Bean
        public EmployeeService employeeService() {
            return Mockito.mock(EmployeeService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = Employee.builder()
                .firstName("Devansh")
                .lastName("Singla")
                .email("d@gmail.com")
                .build();
    }

    @DisplayName("Create Employee")
    @Test
    public void createEmployeeTest() throws Exception {
        // given
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation -> invocation.getArgument(0)
                ));
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
        employeeList.add(employee);
        employeeList.add(Employee.builder().firstName("Aman").lastName("Kumar").email("aman@gmail.com").build());

        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeList);

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

}




















