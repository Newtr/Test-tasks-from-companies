package com.example.MyApp.PropertyView.Application.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.MyApp.PropertyView.Application.DTO.HotelDTO;
import com.example.MyApp.PropertyView.Application.DTO.HotelDetailDTO;
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
}
