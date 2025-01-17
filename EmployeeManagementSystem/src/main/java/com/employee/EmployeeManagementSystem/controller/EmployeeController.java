package com.employee.EmployeeManagementSystem.controller;

import com.employee.EmployeeManagementSystem.dto.EmployeeDTO;
import com.employee.EmployeeManagementSystem.entities.Employee;
import com.employee.EmployeeManagementSystem.exception.EmployeeNotFoundException;
import com.employee.EmployeeManagementSystem.service.DepartmentService;
import com.employee.EmployeeManagementSystem.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/getAllEmployees")
    public String viewHomePage(Model model){
        log.info("Showing All employees");
        model.addAttribute("listEmployees", employeeService.getAllEmployee());
        return "index";
    }

    @GetMapping("/newEmployeeForm")
    public String newEmployeeForm(Model model){
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAllDepartment());
        return "newEmployeeForm";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        try {
            Employee employee = employeeService.dtoToEmployeeConverter(employeeDTO);
            employeeService.saveEmployee(employee);
            log.info("Employee with id: {} successfully saved", employee.getId());
            return "redirect:/";
        } catch (EmployeeNotFoundException ex){
            log.error("Error adding employee with email: {}", employeeDTO.getEmail(), ex);
            model.addAttribute("error", ex.getMessage());
            return newEmployeeForm(model);
        }
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        log.info("Updating employee with id: {}", employeeDTO.getId());
        try {
            Employee employee = employeeService.dtoToEmployeeConverter(employeeDTO);
            employee.setId(employeeDTO.getId());
            employeeService.saveEmployee(employee);
            log.info("Employee with id: {} successfully updated", employeeDTO.getId());
            return "redirect:/";
        } catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            log.error("Error updating employee with id: {}", employeeDTO.getId(), ex);
            return "update_employee";
        }
    }

    @GetMapping("/editEmployee/{id}")
    public String editEmployee(@PathVariable("id") Long id, Model model){
        log.info("Showing edit form for employee with id: {}", id);
        try {
            Employee employee = employeeService.getEmployeeById(id);
            model.addAttribute("departments", departmentService.getAllDepartment());
            model.addAttribute("employee", employeeService.employeeToDtoConverter(employee));
            return "update_employee";
        }catch (EmployeeNotFoundException ex){
            log.error("Error getting employee with id: {}", id, ex);
            model.addAttribute("error", ex.getMessage());
            return viewHomePage(model);
        }

    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }

}
