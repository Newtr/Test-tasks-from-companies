package com.example.MyApp.PropertyView.Application.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.MyApp.PropertyView.Application.DTO.HotelDTO;
import com.example.MyApp.PropertyView.Application.DTO.HotelDetailDTO;
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
}
