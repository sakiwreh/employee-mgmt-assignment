package com.example.EMS.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email format must be valid.")
    private String email;


    @NotBlank(message = "Please provide name.")
    private String firstName;

    private String lastname;


    private String password;

    @NotBlank(message = "Address cannot be blank")
    private String address;
}
