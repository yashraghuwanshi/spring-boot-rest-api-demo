package com.example.controller;

import com.example.dto.EmployeeDto;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/employees")
@Controller
public class FrontController {

    private final EmployeeService employeeService;

    public FrontController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/get")
    public String getEmployees(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }

    @RequestMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
        return "pages/employee_form";
    }

    @RequestMapping(value = "/saveEmployee")
    public String saveEmployee(@ModelAttribute("employeeDto") @Valid EmployeeDto employeeDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form page
            return "pages/employee_form";
        }
        employeeService.save(employeeDto);
        return "redirect:/api/employees/get";
    }

    @RequestMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
        this.employeeService.softDelete(employeeId);
        return "redirect:/api/employees/get";
    }

    @RequestMapping(value = "/showFormForUpdate/{id}")
    public String updateEmployee(@PathVariable(value = "id") Integer employeeId, Model model) {
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        model.addAttribute("employeeDto", employeeDto);
        return "pages/employee_update";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 5;
        Page<EmployeeDto> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);

        List<EmployeeDto> employeeDtoList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("sortReverseDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("employeeDtoList", employeeDtoList);

        return "pages/employee_list";
    }
}