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

    private String name;
    
    // Если потребуется двунаправленная связь с отелями, можно добавить:
    // @ManyToMany(mappedBy = "amenities")
    // private List<HotelEntity> hotels;
}
