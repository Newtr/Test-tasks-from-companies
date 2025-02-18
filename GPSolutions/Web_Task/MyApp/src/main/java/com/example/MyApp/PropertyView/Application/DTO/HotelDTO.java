package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;
import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import com.example.MyApp.PropertyView.Domain.Model.Address;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String description;
    private String address;       
    private String phone;
    
    public static HotelDTO fromHotel(Hotel hotel) {
        Address addr = hotel.getAddress();
        
        HotelDTO response = new HotelDTO();
        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setDescription(hotel.getDescription());
        response.setAddress(String.format("%d %s, %s, %s, %s", 
            addr.getHouseNumber(),
            addr.getStreet(),
            addr.getCity(),
            addr.getPostCode(),
            addr.getCounty()
        ));
        response.setPhone(hotel.getContacts().getPhone());
        
        return response;
    }
}
