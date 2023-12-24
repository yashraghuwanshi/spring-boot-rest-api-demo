package com.example.dto;

import com.example.validation.ValidRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Integer id;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min = 2, message = "first name should have at least 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 2, message = "last name should have at least 2 characters")
    private String lastName;

    @JsonIgnore
    private String hireDate;

    //@NotBlank(message = "Mobile number is required")
    //@Digits(integer = 10, fraction = 0, message = "Mobile number must be 10 digits")
    //@Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    //@Pattern(regexp = "^[0-9]*$", message = "Mobile number must contain only digits")
    @DecimalMin(value = "1000000000", message = "Mobile number must be 10 digits")
    @DecimalMax(value = "9999999999", message = "Mobile number must be 10 digits")
    private Long phone;

    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^\\w+(\\.\\w+)*@gmail\\.com$", message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 8, message = "username should have at least 8 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.")
    private String password;

    @ValidRole
    private String role;

    @JsonIgnore
    private Boolean isDeleted;
}
