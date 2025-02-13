package com.example.MyApp.PropertyView.Domain.Ports;

import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import java.util.List;
import java.util.Optional;

public interface HotelPort {
    List<Hotel> findAllHotels();
    Optional<Hotel> findHotelById(Long id);
}

