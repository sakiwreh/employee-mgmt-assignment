package com.example.EMS.service.impl;

import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.entity.Employee;
import com.example.EMS.repository.EmployeeRepository;
import com.example.EMS.service.IEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if(employee == null) throw new RuntimeException("Employee with id {} not present"+id);
        return convertToDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .map(emp -> convertToDto(emp))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = modelMapper.map(employeeRequestDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }

    @Override
    public EmployeeResponseDto updateEmployeeById(Long id, EmployeeRequestDto employeeRequestDto) {
        return null;
    }

    @Override
    public EmployeeResponseDto partialUpdateemployeeById(Long id, EmployeeRequestDto employeeRequestDto) {
        return null;
    }

    private EmployeeResponseDto convertToDto(Employee employee){
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }
}
