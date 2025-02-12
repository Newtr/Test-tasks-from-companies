package com.example.MyApp.PropertyView.Domain.Model;

import lombok.Data;

@Data
public class Address {
    private Integer houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;
}
