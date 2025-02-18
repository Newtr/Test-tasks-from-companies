package com.example.MyApp.PropertyView.Application.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddAmenitiesRequest {
    @NotNull
    private List<@NotBlank String> amenities;
}
