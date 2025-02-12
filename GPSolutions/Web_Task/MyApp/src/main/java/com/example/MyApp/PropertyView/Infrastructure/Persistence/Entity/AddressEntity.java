package com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;
}
