package com.example.EMS.service.impl;

import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.entity.Employee;
import com.example.EMS.repository.EmployeeRepository;
import com.example.EMS.service.IEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;


    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found!!"));
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
        Employee existing = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found."));
        modelMapper.map(employeeRequestDto,existing);
        Employee saved = employeeRepository.save(existing);
        return convertToDto(saved);
    }

    @Override
    public EmployeeResponseDto partialUpdateemployeeById(Long id, EmployeeRequestDto employeeRequestDto) {
        Employee existing = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found."));
        if(employeeRequestDto.getFirstName() != null)existing.setFirstName(employeeRequestDto.getFirstName());
        if(employeeRequestDto.getEmail() != null)existing.setEmail(employeeRequestDto.getEmail());
        if(employeeRequestDto.getAddress() != null)existing.setAddress(employeeRequestDto.getAddress());

        Employee saved = employeeRepository.save(existing);
        return convertToDto(saved);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("Employee doesn't exist!!");
        }

        employeeRepository.deleteById(id);

    }

    @Override
    public String uploadImage(Long id, MultipartFile file) throws IOException {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found!"));

        String projectPath = System.getProperty("user.dir");
        File folder = new File(projectPath + File.separator + uploadDir);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String fileName = "emp_"+id+".png";
        File destinationFile = new File(folder,fileName);
        file.transferTo(destinationFile);

        String imageUrl = baseUrl + "/v1/employees/"+id+"/image";

        employee.setProfileImageUrl(imageUrl);
        employeeRepository.save(employee);

        return imageUrl;
    }

    @Override
    public byte[] getImage(Long id) throws IOException {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found!"));
        String projectPath = System.getProperty("user.dir");
        String fileName = "emp_"+id+".png";
        File file = new File(projectPath+File.separator+uploadDir,fileName);

        if(!file.exists()) throw new RuntimeException("Image not found for the employee.");
        return Files.readAllBytes(file.toPath());
    }

    private EmployeeResponseDto convertToDto(Employee employee){
        return modelMapper.map(employee, EmployeeResponseDto.class);
    }
}
