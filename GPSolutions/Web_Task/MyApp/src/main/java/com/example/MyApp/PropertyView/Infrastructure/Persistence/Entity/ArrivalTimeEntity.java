package com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "arrival_times")
public class ArrivalTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkIn;
    private String checkOut;
}
