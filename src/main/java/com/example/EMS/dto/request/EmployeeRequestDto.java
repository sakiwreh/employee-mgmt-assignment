package com.example.EMS.dto.request;

import com.example.EMS.validations.groups.Create;
import com.example.EMS.validations.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank(message = "Email cannot be blank.", groups = {Create.class})
    @Email(message = "Email format must be valid.", groups = {Create.class, Update.class})
    private String email;


    @NotBlank(message = "Please provide name.",groups = {Create.class, Update.class})
    private String firstName;

    private String lastname;

    @NotNull(message = "Password must not be null",groups = {Create.class})
    private String password;

    @NotBlank(message = "Address cannot be blank")
    private String address;
}
