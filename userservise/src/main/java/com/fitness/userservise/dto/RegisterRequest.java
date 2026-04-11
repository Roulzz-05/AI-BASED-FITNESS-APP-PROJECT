package com.fitness.userservise.dto;
//import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank(message = "Email is required")
    @Email (message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6, message="Password must have at least 6 characters long")
    private String password;
    private String firstname;
    private String lastname;



}
