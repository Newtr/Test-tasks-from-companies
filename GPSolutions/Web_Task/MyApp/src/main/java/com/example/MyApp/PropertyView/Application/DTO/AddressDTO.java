package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;
import com.example.MyApp.PropertyView.Domain.Model.Address;

@Data
public class AddressDTO {
    private Integer houseNumber;
    private String street;
    private String city;
    private String county;
    private String postCode;
    
    public static AddressDTO fromAddress(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO dto = new AddressDTO();
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCounty(address.getCounty());
        dto.setPostCode(address.getPostCode());
        return dto;
    }
}
