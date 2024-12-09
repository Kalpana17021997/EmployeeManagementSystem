package com.employee.EmployeeManagementSystem.service;


import com.employee.EmployeeManagementSystem.dto.DepartmentDTO;
import com.employee.EmployeeManagementSystem.entities.Department;
import com.employee.EmployeeManagementSystem.exception.DepartmentNotFoundException;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentById(Long id) throws DepartmentNotFoundException;

    void saveDepartment(Department department);

    List<DepartmentDTO> getAllDepartment();

    void deleteDepartment(Long id);

    DepartmentDTO departmentToDTOConverter(Department department);

    Department dtoToDepartmentConverter(DepartmentDTO departmentDTO);
    Department updateDepartment(Long id,Department department);

}
