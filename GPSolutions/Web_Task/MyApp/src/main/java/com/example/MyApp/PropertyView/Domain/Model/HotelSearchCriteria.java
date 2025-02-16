package com.example.MyApp.PropertyView.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelSearchCriteria {
    private String name;
    private String brand;
    private String city;
    private String county;
    private List<String> amenities;
}
