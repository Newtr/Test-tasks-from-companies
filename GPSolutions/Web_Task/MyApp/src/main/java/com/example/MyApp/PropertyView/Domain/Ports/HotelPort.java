package com.example.MyApp.PropertyView.Domain.Ports;

import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import com.example.MyApp.PropertyView.Domain.Model.HotelSearchCriteria;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HotelPort {
    List<Hotel> findAllHotels();
    Optional<Hotel> findHotelById(Long id);
    List<Hotel> searchHotels(HotelSearchCriteria criteria);
    Map<String, Long> getBrandHistogram(List<String> filterValues);
    Map<String, Long> getCityHistogram(List<String> filterValues);
    Map<String, Long> getCountyHistogram(List<String> filterValues);
    Map<String, Long> getAmenitiesHistogram(List<String> filterValues);
    Hotel saveHotel(Hotel hotel);
}

