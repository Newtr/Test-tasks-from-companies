package com.example.MyApp.PropertyView.Application.DTO;

import lombok.Data;
import com.example.MyApp.PropertyView.Domain.Model.ArrivalTime;

@Data
public class ArrivalTimeDTO {
    private String checkIn;
    private String checkOut;
    
    public static ArrivalTimeDTO fromArrivalTime(ArrivalTime arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }
        ArrivalTimeDTO dto = new ArrivalTimeDTO();
        dto.setCheckIn(arrivalTime.getCheckIn());
        dto.setCheckOut(arrivalTime.getCheckOut());
        return dto;
    }
}
