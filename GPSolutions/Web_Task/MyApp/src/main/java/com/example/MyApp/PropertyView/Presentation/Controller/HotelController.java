package com.example.MyApp.PropertyView.Presentation.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyApp.PropertyView.Application.DTO.AddAmenitiesRequest;
import com.example.MyApp.PropertyView.Application.DTO.CreateHotelRequest;
import com.example.MyApp.PropertyView.Application.DTO.HotelDTO;
import com.example.MyApp.PropertyView.Application.DTO.HotelDetailDTO;
import com.example.MyApp.PropertyView.Application.Service.HotelService;
import com.example.MyApp.PropertyView.Domain.Model.Hotel;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/property-view/hotels")
@RequiredArgsConstructor
@Validated
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

    @GetMapping("/search")
    public List<HotelDTO> searchHotels(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String county,
        @RequestParam(required = false) List<String> amenities
    ) {
        return hotelService.searchHotels(name, brand, city, county, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(
        @PathVariable String param,
        @RequestParam(required = false) List<String> filterValues
    ) {
        if (!List.of("brand", "city", "county", "amenities").contains(param)) {
            throw new IllegalArgumentException("Invalid parameter: " + param);
        }
        return hotelService.getHistogram(param, filterValues);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelDTO createHotel(@Valid @RequestBody CreateHotelRequest request) {
        Hotel hotel = hotelService.createHotel(request);
        return HotelDTO.fromHotel(hotel);
    }

    @PostMapping("/{id}/amenities")
    public HotelDTO addAmenitiesToHotel(
        @PathVariable Long id,
        @Valid @RequestBody AddAmenitiesRequest request
    ) {
        Hotel hotel = hotelService.addAmenities(id, request.getAmenities());
        return HotelDTO.fromHotel(hotel);
    }
}
