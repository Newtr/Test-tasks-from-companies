package com.example.MyApp.PropertyView.Presentation.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyApp.PropertyView.Application.DTO.HotelDTO;
import com.example.MyApp.PropertyView.Application.DTO.HotelDetailDTO;
import com.example.MyApp.PropertyView.Application.Service.HotelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/property-view/hotels")
@RequiredArgsConstructor
public class HotelController {
    
    private final HotelService hotelService;

    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailDTO> getHotelById(@PathVariable Long id) {
        HotelDetailDTO hotel = hotelService.getHotelById(id);
        return hotel != null ? ResponseEntity.ok(hotel) : ResponseEntity.notFound().build();
    }
}
