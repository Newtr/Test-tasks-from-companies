package com.example.MyApp.PropertyView.Application.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequest {
    @NotNull
    private Integer houseNumber;
    
    @NotBlank
    private String street;
    
    @NotBlank
    private String city;
    
    @NotBlank
    private String county;
    
    @NotBlank
    private String postCode;
}
