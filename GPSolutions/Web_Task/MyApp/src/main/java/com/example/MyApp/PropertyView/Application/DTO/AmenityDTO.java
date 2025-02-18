package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;

import com.example.MyApp.PropertyView.Domain.Model.Amenity;

@Data
public class AmenityDTO {
    private String name;
    
    public static AmenityDTO fromAmenity(Amenity amenity) {
        if (amenity == null) {
            return null;
        }
        AmenityDTO dto = new AmenityDTO();
        dto.setName(amenity.getName());
        return dto;
    }
}
