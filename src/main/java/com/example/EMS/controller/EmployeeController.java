package com.example.EMS.controller;

import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.service.impl.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto){
        EmployeeResponseDto saved = employeeService.createEmployee(employeeRequestDto);
        if(saved == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("demo of header","Employee created")
                .body(saved);
    }

    @GetMapping
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
    public  ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
}