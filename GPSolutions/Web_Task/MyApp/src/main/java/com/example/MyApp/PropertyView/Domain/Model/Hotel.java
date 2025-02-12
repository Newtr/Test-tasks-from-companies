package com.example.MyApp.PropertyView.Domain.Model;

import lombok.Data;
import java.util.List;

@Data
public class Hotel {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private Address address;
    private Contacts contacts;
    private ArrivalTime arrivalTime;
    private List<Amenity> amenities; 
}
