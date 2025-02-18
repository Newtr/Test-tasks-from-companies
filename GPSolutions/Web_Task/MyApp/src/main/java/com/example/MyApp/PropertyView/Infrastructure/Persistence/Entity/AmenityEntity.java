package com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "amenities")
public class AmenityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
