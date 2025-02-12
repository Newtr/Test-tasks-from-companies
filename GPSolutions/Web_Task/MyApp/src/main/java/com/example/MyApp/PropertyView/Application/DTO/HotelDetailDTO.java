package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;
import com.example.MyApp.PropertyView.Domain.Model.Hotel;

@Data
public class HotelDetailDTO {
    private Long id;
    private String name;
    private String brand;
    private AddressDTO address;
    private ContactsDTO contacts;
    private ArrivalTimeDTO arrivalTime;
    private List<String> amenities;
    
    public static HotelDetailDTO fromHotel(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        HotelDetailDTO dto = new HotelDetailDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setBrand(hotel.getBrand());
        dto.setAddress(AddressDTO.fromAddress(hotel.getAddress()));
        dto.setContacts(ContactsDTO.fromContacts(hotel.getContacts()));
        dto.setArrivalTime(ArrivalTimeDTO.fromArrivalTime(hotel.getArrivalTime()));
        if (hotel.getAmenities() != null) {
            dto.setAmenities(
                hotel.getAmenities()
                     .stream()
                     .map(amenity -> amenity.getName())
                     .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
