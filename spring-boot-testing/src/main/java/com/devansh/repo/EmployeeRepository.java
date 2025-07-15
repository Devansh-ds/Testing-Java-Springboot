package com.devansh.repo;

import com.devansh.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("""
    select e from Employee e where e.firstName = ?1 and e.lastName = ?2
""")
    Employee findByJPQL(String firstName, String lastName);

    @Query("""
    select e from Employee e where e.firstName = :firstName and e.lastName = :lastName
""")
    Employee findByJPQLNamedParmas(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
