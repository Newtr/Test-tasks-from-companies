package com.example.MyApp.PropertyView.Application.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.example.MyApp.PropertyView.Application.DTO.CreateHotelRequest;
import com.example.MyApp.PropertyView.Application.DTO.HotelDTO;
import com.example.MyApp.PropertyView.Application.DTO.HotelDetailDTO;
import com.example.MyApp.PropertyView.Domain.ResourceNotFoundException;
import com.example.MyApp.PropertyView.Domain.Model.Address;
import com.example.MyApp.PropertyView.Domain.Model.Amenity;
import com.example.MyApp.PropertyView.Domain.Model.ArrivalTime;
import com.example.MyApp.PropertyView.Domain.Model.Contacts;
import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import com.example.MyApp.PropertyView.Domain.Model.HotelSearchCriteria;
import com.example.MyApp.PropertyView.Domain.Ports.HotelPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotelService {
    
    private final HotelPort hotelPort;

    public List<HotelDTO> getAllHotels() {
        return hotelPort.findAllHotels().stream()
                .map(HotelDTO::fromHotel)
                .collect(Collectors.toList());
    }

    public HotelDetailDTO getHotelById(Long id) {
        return hotelPort.findHotelById(id)
                .map(HotelDetailDTO::fromHotel)
                .orElse(null);
    }

    public List<HotelDTO> searchHotels(
    String name, 
    String brand, 
    String city, 
    String county, 
    List<String> amenities
    ) {
        HotelSearchCriteria criteria = new HotelSearchCriteria(
            name, brand, city, county, amenities
        );
        return hotelPort.searchHotels(criteria)
            .stream()
            .map(HotelDTO::fromHotel)
            .collect(Collectors.toList());
    }

    public Map<String, Long> getHistogram(String param, List<String> filterValues) {
        return switch (param.toLowerCase()) {
            case "brand" -> hotelPort.getBrandHistogram(filterValues);
            case "city" -> hotelPort.getCityHistogram(filterValues);
            case "county" -> hotelPort.getCountyHistogram(filterValues);
            case "amenities" -> hotelPort.getAmenitiesHistogram(filterValues);
            default -> throw new IllegalArgumentException("Invalid parameter");
        };
    }

    public Hotel createHotel(CreateHotelRequest request) {
    Hotel hotel = convertToDomain(request);
    return hotelPort.saveHotel(hotel);
    }

    private Hotel convertToDomain(CreateHotelRequest request) {
        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setBrand(request.getBrand());
        
        Address address = new Address();
        address.setHouseNumber(request.getAddress().getHouseNumber());
        address.setStreet(request.getAddress().getStreet());
        address.setCity(request.getAddress().getCity());
        address.setCounty(request.getAddress().getCounty());
        address.setPostCode(request.getAddress().getPostCode());
        hotel.setAddress(address);
        
        Contacts contacts = new Contacts();
        contacts.setPhone(request.getContacts().getPhone());
        contacts.setEmail(request.getContacts().getEmail());
        hotel.setContacts(contacts);
        
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(request.getArrivalTime().getCheckIn());
        arrivalTime.setCheckOut(request.getArrivalTime().getCheckOut());
        hotel.setArrivalTime(arrivalTime);
        
        if (request.getAmenities() != null) {
            hotel.setAmenities(
                request.getAmenities().stream()
                    .map(amenityName -> {
                        Amenity amenity = new Amenity();
                        amenity.setName(amenityName);
                        return amenity;
                    })
                    .collect(Collectors.toList())
            );
        }
        
        return hotel;
    }

    public Hotel addAmenities(Long hotelId, List<String> amenityNames) {
    Hotel hotel = hotelPort.findHotelById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        
    List<Amenity> newAmenities = amenityNames.stream()
        .map(name -> {
            Amenity amenity = new Amenity();
            amenity.setName(name);
            return amenity;
        })
        .collect(Collectors.toList());
    
    if (hotel.getAmenities() == null) {
        hotel.setAmenities(new ArrayList<>());
    }
    hotel.getAmenities().addAll(newAmenities);
    
    return hotelPort.saveHotel(hotel);
    }
}
