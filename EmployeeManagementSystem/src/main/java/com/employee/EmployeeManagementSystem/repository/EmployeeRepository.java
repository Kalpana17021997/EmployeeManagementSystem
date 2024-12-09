package com.employee.EmployeeManagementSystem.repository;

import com.employee.EmployeeManagementSystem.entities.Department;
import com.employee.EmployeeManagementSystem.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(Department department);
    Employee findByEmail(String email);

}
