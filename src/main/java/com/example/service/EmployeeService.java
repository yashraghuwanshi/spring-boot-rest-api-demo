package com.example.service;

import com.example.dto.EmployeeDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    EmployeeDto save(EmployeeDto employeeDto);

    List<EmployeeDto> saveAll(List<EmployeeDto> employeesDto);

    EmployeeDto getById(Integer id);

    List<EmployeeDto> getAll();

    EmployeeDto update(Integer id, EmployeeDto employeeDto);

    EmployeeDto updateByField(Integer id, Map<String, Object> fields);

    void deleteById(Integer id);

    void deleteAll();

    EmployeeDto findByEmail(String email);

    List<EmployeeDto> findAllNonDeleted();

    void softDelete(Integer id);

}
