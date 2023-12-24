package com.example.service;

import com.example.dto.EmployeeDto;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> saveAll(List<EmployeeDto> employeesDto) {
        List<Employee> employees = new ArrayList<>();
        for (EmployeeDto employeeDto : employeesDto) {
            Employee employee = modelMapper.map(employeeDto, Employee.class);
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
        return employeesDto;
    }

    @Override
    public EmployeeDto getById(Integer id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " doesn't exist in database."));

        return modelMapper.map(employee, EmployeeDto.class);

    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDto> employeesDto = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            employeesDto.add(employeeDto);
        }
        return employeesDto;
    }

    @Override
    public EmployeeDto update(Integer id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " doesn't exist in database."));
        if (existingEmployee != null) {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            //existingEmployee.setHireDate(employeeDto.getHireDate());
            existingEmployee.setPhone(employeeDto.getPhone());
            existingEmployee.setEmail(employeeDto.getEmail());
            existingEmployee.setUsername(employeeDto.getUsername());
            existingEmployee.setRole(employeeDto.getRole());
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return modelMapper.map(updatedEmployee, EmployeeDto.class);
        }
        return null;
    }

    @Override
    public EmployeeDto updateByField(Integer id, Map<String, Object> fields) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " doesn't exist in database."));
        fields.forEach((fieldName, fieldValue) -> {
            Field field = ReflectionUtils.findField(Employee.class, fieldName);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingEmployee, fieldValue);
            }
        });
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

//    @Override
//    public EmployeeDto updateByField(Integer id, Map<String, Object> fields) {
//        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " doesn't exist in database."));
//
//        System.out.println(fields);
//
//        for(Map.Entry<String, Object> field : fields.entrySet()){
//            String fieldName = field.getKey();
//            Object fieldValue = field.getValue();
//
//            try {
//                Field declaredField = Employee.class.getDeclaredField(fieldName);
//                declaredField.setAccessible(true);
//                declaredField.set(existingEmployee, fieldValue);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                System.out.println("Exception caught: " + e.getMessage());
//            }
//        }
//        Employee updatedEmployee = employeeRepository.save(existingEmployee);
//        return modelMapper.map(updatedEmployee, EmployeeDto.class);
//    }


    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> findAllNonDeleted() {
        List<Employee> employees = employeeRepository.findAllNonDeleted();
        List<EmployeeDto> employeesDto = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
            employeesDto.add(employeeDto);
        }
        return employeesDto;
    }

    @Override
    public void softDelete(Integer id) {
        employeeRepository.softDelete(id);
    }
}
