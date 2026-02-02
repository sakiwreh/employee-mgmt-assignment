package com.example.EMS.service;


import com.example.EMS.dto.request.EmployeeRequestDto;
import com.example.EMS.dto.response.EmployeeResponseDto;
import com.example.EMS.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeService {

      EmployeeResponseDto getEmployeeById(Long id);
      List<EmployeeResponseDto> getAllEmployees();
      EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
      EmployeeResponseDto updateEmployeeById(Long id, EmployeeRequestDto employeeRequestDto);
      EmployeeResponseDto partialUpdateemployeeById(Long id, EmployeeRequestDto employeeRequestDto);



//
//    BookDto createBook(BookDto bookDto);
//    //Page<BookDto> getAllBooks(Pageable pageable);
//    List<BookDto> getBooksByAuthor(String authorName);
//    List<BookDto> getBookBeforeYear(Integer Year);
//    Page<Book> getBooks(int page, int size, String sortBy, String sortDir);
}
