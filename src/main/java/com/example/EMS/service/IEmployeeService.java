package com.example.EMS.service;


import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {

      EmployeeResponseDto getEmployeeById(Long id);

      List<EmployeeResponseDto> getAllEmployees();

      EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);

      EmployeeResponseDto updateEmployeeById(Long id, EmployeeRequestDto employeeRequestDto);

      EmployeeResponseDto partialUpdateemployeeById(Long id, EmployeeRequestDto employeeRequestDto);

      void deleteEmployeeById(Long id);
      String uploadImage(Long id, MultipartFile file) throws IOException;
      byte[] getImage(Long id) throws IOException;
}
