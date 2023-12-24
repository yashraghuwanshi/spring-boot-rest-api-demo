package com.example.controller;

import com.example.dto.EmployeeDto;
import com.example.exception.ResourceNotFoundException;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/employees")
@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
        logger.info("Hello endpoint accessed.");
        String response = "Welcome to the Spring Boot REST API application";
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/v1/save")
    public ResponseEntity<EmployeeDto> save(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeDto1 = employeeService.save(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto1);
    }

    @PostMapping(value = "/v1/saveAll")
    public ResponseEntity<List<EmployeeDto>> saveAll(@RequestBody List<EmployeeDto> employeesDto) {
        List<EmployeeDto> employeesDto1 = employeeService.saveAll(employeesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesDto1);
    }

    @GetMapping(value = "/v1/getById/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Integer id) {
        logger.info("Getting employee with id: {}", id);
        EmployeeDto employeeDto = employeeService.getById(id);
        logger.warn("Employee found with id: {}", id);
        return ResponseEntity.ok(employeeDto);
    }

    @RequestMapping(value = "/v1/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDto>> getAll() {
        logger.info("Endpoint /v1/getAll called. Retrieving all employees.");
        List<EmployeeDto> employeesDto = employeeService.getAll();

        if (employeesDto.isEmpty()) {
            logger.warn("No employees found!");
        } else {
            logger.debug("Retrieved {} employees", employeesDto.size());
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeesDto);
    }

    @GetMapping(value = "/v1/getAllNonDeleted")
    public ResponseEntity<List<EmployeeDto>> getAllNonDeleted() {
        List<EmployeeDto> employeesDto = employeeService.findAllNonDeleted();
        return ResponseEntity.ok(employeesDto);
    }

    @PutMapping(value = "/v1/update/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Integer id, @Valid @RequestBody EmployeeDto employeeDto) {
        logger.info("Endpoint /v1/update/{} called. Updating employee: {}", id, employeeDto);
        EmployeeDto updatedEmployeeDto = employeeService.update(id, employeeDto);
        logger.debug("Employee with ID {} updated: {}", id, updatedEmployeeDto);
        return ResponseEntity.ok(updatedEmployeeDto);
    }

    @PatchMapping(value = "/v1/updateByField/{id}")
    public ResponseEntity<EmployeeDto> updateByField(@PathVariable Integer id, @RequestBody Map<String, Object> fields) {
            EmployeeDto updatedEmployee = employeeService.updateByField(id, fields);
            return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(value = "/v1/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Endpoint /v1/delete/{} called. Deleting employee with ID: {}", id, id);
        employeeService.deleteById(id);
        logger.debug("Employee with ID {} deleted.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/v1/deleteAll/{id}")
    public ResponseEntity<Void> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/v1/getByEmail/{email}")
    public ResponseEntity<EmployeeDto> findByEmail(@PathVariable String email) {
        EmployeeDto employeeDto = employeeService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDto);
    }

    @GetMapping(value = "/v1/getByRole")
    public ResponseEntity<List<EmployeeDto>> findByRole(@RequestParam String role) {
        List<EmployeeDto> employeesDto = employeeService.findByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(employeesDto);
    }

    @DeleteMapping(value = "v1/softDelete/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Integer id) {
        employeeService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

}
