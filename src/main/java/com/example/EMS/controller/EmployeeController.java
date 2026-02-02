package com.example.EMS.controller;

import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.service.impl.EmployeeService;
import com.example.EMS.validations.groups.Create;
import com.example.EMS.validations.groups.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("v1/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create Employee", description = "Creates a new employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Validated(Create.class) @RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeResponseDto saved = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("demo of header","Employee created")
                .body(saved);
    }

    @GetMapping
    @Operation(summary = "Get All Employees", description = "Get all employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Employee By Id",
            description = "Fetches employee by unique id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "Employee not found"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    public  ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Updates an entire employee record")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @Validated(Update.class) @RequestBody EmployeeRequestDto employeeRequestDto){
        return ResponseEntity.ok(employeeService.updateEmployeeById(id,employeeRequestDto));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partial update", description = "Updates specific fields of employee")
    public ResponseEntity<EmployeeResponseDto> partialUpdateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDto employeeRequestDto){
        return ResponseEntity.ok(employeeService.partialUpdateemployeeById(id,employeeRequestDto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleting Employee")
    public ResponseEntity<String> deleteemployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PostMapping(value = "/{id}/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file")MultipartFile file) throws IOException{
        String imageUrl = employeeService.uploadImage(id,file);
        return ResponseEntity.ok("Image uploaded successfully."+imageUrl);
    }

    @GetMapping(value = "/{id}/image")
    @Operation(summary = "Get image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException{
        byte[] imageData = employeeService.getImage(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageData);
    }
}