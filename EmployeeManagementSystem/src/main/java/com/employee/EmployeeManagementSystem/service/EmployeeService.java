package com.employee.EmployeeManagementSystem.service;


import com.employee.EmployeeManagementSystem.dto.EmployeeDTO;
import com.employee.EmployeeManagementSystem.entities.Employee;
import com.employee.EmployeeManagementSystem.exception.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployee();

    void saveEmployee(Employee employee) throws EmployeeNotFoundException;

    Employee getEmployeeById(Long id) throws EmployeeNotFoundException;

    void deleteEmployee(Long id);

    Employee dtoToEmployeeConverter(EmployeeDTO employeeDTO);
    EmployeeDTO employeeToDtoConverter(Employee employee);
}
