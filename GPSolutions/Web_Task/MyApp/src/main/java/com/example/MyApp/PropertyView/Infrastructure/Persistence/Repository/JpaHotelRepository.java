package com.example.MyApp.PropertyView.Infrastructure.Persistence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.HotelEntity;

public interface JpaHotelRepository extends JpaRepository<HotelEntity, Long> {
}
