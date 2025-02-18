package com.example.MyApp.PropertyView.Application.DTO;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHotelRequest {
    @NotBlank
    private String name;
    
    private String description;
    
    @NotBlank
    private String brand;

    @Valid
    @NotNull
    private AddressRequest address;

    @Valid
    @NotNull
    private ContactsRequest contacts;

    @Valid
    @NotNull
    private ArrivalTimeRequest arrivalTime;

    private List<String> amenities;
}