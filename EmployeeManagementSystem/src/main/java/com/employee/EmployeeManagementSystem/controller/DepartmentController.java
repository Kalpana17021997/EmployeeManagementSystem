package com.employee.EmployeeManagementSystem.controller;

import com.employee.EmployeeManagementSystem.dto.DepartmentDTO;
import com.employee.EmployeeManagementSystem.entities.Department;
import com.employee.EmployeeManagementSystem.exception.DepartmentNotFoundException;
import com.employee.EmployeeManagementSystem.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentController.class);


    @Autowired
    DepartmentService departmentService;


    @GetMapping("")
    public String getAllDepartment(Model model){
        log.info("Getting all departments data");
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "departments";
    }

    @GetMapping("/{id}")
    public String getDepartmentByid(@PathVariable("id") Long id, Model model) {
        log.info("Getting department data by id:{}", id);
        try {
            Department department =departmentService.getDepartmentById(id);
            DepartmentDTO departmentDTO = departmentService.departmentToDTOConverter(department);
            model.addAttribute("department", departmentDTO);
            return "update_department";
        } catch (DepartmentNotFoundException ex) {
            log.error("Error getting department with id: {}", id, ex);
            model.addAttribute("error", ex.getMessage());
            return getAllDepartment(model);
        }
    }

    @GetMapping("/add")
    public String addDepartment(Model model){
        model.addAttribute("department", new DepartmentDTO());
        return "add_department";
    }

    @PostMapping("")
    public String addDepartment(@ModelAttribute("department") DepartmentDTO departmentDTO){
        Department department = departmentService.dtoToDepartmentConverter(departmentDTO);
        departmentService.saveDepartment(department);
        return "redirect:/department";
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable("id") Long id){
        departmentService.deleteDepartment(id);
        return "redirect:/department";
    }

}

