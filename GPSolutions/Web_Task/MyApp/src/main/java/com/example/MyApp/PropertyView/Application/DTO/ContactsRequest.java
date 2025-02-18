package com.example.MyApp.PropertyView.Application.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactsRequest {
    @NotBlank
    private String phone;
    
    @Email
    @NotBlank
    private String email;
}
