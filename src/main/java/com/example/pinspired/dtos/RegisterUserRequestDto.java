package com.example.pinspired.dtos;

import com.example.pinspired.infrastructure.validations.AgeBetween;
import com.example.pinspired.infrastructure.validations.AtLeast18Years;
import com.example.pinspired.infrastructure.validations.StartsWith;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDto {

    @Size(min = 3, max = 50, message = "Username should be between 3 and 50 characters")
    @NotBlank(message = "Username should not be empty or blank")
//    @NotNull(message = "Username is required")
    private String username;

    @Size(min = 3, max = 50, message = "Email should be between 3 and 50 characters")
    @NotBlank(message = "Email should not be empty or blank")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    @NotBlank(message = "Name should not be empty or blank")
    @NotNull(message = "Name is required")
    private String name;

    @Size(min = 3, max = 50, message = "Surname should be between 3 and 50 characters")
    @NotBlank(message = "Surname should not be empty or blank")
    @NotNull(message = "Surname is required")
    private String surname;
    private String role = "CUSTOMER";

    @Size(min = 9, max = 20, message = "Phone should be between 9 and 20 characters")
    @NotBlank(message = "Phone should not be empty or blank")
    @NotNull(message = "Phone is required")
    @StartsWith(value = "+383", message = "Phone should start with +383")
    private String phone;

    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate should be in the past")
    @AtLeast18Years(message = "You should be at least 18 years old")
    @AgeBetween(min = 18, max = 64, message = "You should be between 18 and 64 years old")
    private LocalDate birthdate;

    @Size(min = 6, max = 100, message = "Password should be between 6 and 100 characters")
    @NotBlank(message = "Password should not be empty or blank")
    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password should contain at least one uppercase letter, one lowercase letter and one digit")
    private String password;

    @Size(min = 6, max = 100, message = "Password should be between 6 and 100 characters")
    @NotBlank(message = "Password should not be empty or blank")
    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password should contain at least one uppercase letter, one lowercase letter and one digit")
    //@SameAs('password', message = "Passwords should match")
    private String confirmPassword;


    @AssertTrue(message = "You should accept the terms and conditions")
    private boolean acceptTerms;
}





