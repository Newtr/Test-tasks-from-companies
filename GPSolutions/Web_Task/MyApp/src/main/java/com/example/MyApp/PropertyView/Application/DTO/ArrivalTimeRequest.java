package com.example.MyApp.PropertyView.Application.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ArrivalTimeRequest {
    @Pattern(regexp = "^([0-1]?\\d|2[0-3]):[0-5]\\d$")
    @NotBlank
    private String checkIn;
    
    @Pattern(regexp = "^([0-1]?\\d|2[0-3]):[0-5]\\d$")
    private String checkOut;
}
