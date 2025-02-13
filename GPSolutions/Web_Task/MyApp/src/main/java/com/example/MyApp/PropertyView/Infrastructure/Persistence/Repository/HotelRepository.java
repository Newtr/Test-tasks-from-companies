package com.example.MyApp.PropertyView.Infrastructure.Persistence.Repository;

import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import com.example.MyApp.PropertyView.Domain.Model.Address;
import com.example.MyApp.PropertyView.Domain.Model.Contacts;
import com.example.MyApp.PropertyView.Domain.Model.ArrivalTime;
import com.example.MyApp.PropertyView.Domain.Model.Amenity;
import com.example.MyApp.PropertyView.Domain.Ports.HotelPort;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.HotelEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.AddressEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.ContactsEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.ArrivalTimeEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.AmenityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HotelRepository implements HotelPort {

    private final JpaHotelRepository jpaHotelRepository;

    @Override
    public List<Hotel> findAllHotels() {
        return jpaHotelRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Hotel> findHotelById(Long id) {
        return jpaHotelRepository.findById(id)
                .map(this::mapToDomain);
    }

    private Hotel mapToDomain(HotelEntity entity) {
        Hotel hotel = new Hotel();
        hotel.setId(entity.getId());
        hotel.setName(entity.getName());
        hotel.setDescription(entity.getDescription());
        hotel.setBrand(entity.getBrand());
        
        if (entity.getAddress() != null) {
            hotel.setAddress(convertAddress(entity.getAddress()));
        }
        if (entity.getContacts() != null) {
            hotel.setContacts(convertContacts(entity.getContacts()));
        }
        if (entity.getArrivalTime() != null) {
            hotel.setArrivalTime(convertArrivalTime(entity.getArrivalTime()));
        }
        if (entity.getAmenities() != null) {
            hotel.setAmenities(entity.getAmenities().stream()
                    .map(this::convertAmenity)
                    .collect(Collectors.toList()));
        }
        return hotel;
    }

    private Address convertAddress(AddressEntity addressEntity) {
        Address address = new Address();
        address.setHouseNumber(addressEntity.getHouseNumber());
        address.setStreet(addressEntity.getStreet());
        address.setCity(addressEntity.getCity());
        address.setCounty(addressEntity.getCounty());
        address.setPostCode(addressEntity.getPostCode());
        return address;
    }

    private Contacts convertContacts(ContactsEntity contactsEntity) {
        Contacts contacts = new Contacts();
        contacts.setPhone(contactsEntity.getPhone());
        contacts.setEmail(contactsEntity.getEmail());
        return contacts;
    }

    private ArrivalTime convertArrivalTime(ArrivalTimeEntity arrivalTimeEntity) {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(arrivalTimeEntity.getCheckIn());
        arrivalTime.setCheckOut(arrivalTimeEntity.getCheckOut());
        return arrivalTime;
    }

    private Amenity convertAmenity(AmenityEntity amenityEntity) {
        Amenity amenity = new Amenity();
        amenity.setName(amenityEntity.getName());
        return amenity;
    }
}
