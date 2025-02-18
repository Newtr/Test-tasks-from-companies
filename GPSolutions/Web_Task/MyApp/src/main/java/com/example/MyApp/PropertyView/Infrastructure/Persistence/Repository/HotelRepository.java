package com.example.MyApp.PropertyView.Infrastructure.Persistence.Repository;

import com.example.MyApp.PropertyView.Domain.Model.Hotel;
import com.example.MyApp.PropertyView.Domain.Model.HotelSearchCriteria;
import com.example.MyApp.PropertyView.Domain.Model.Address;
import com.example.MyApp.PropertyView.Domain.Model.Contacts;
import com.example.MyApp.PropertyView.Domain.Model.ArrivalTime;
import com.example.MyApp.PropertyView.Domain.Model.Amenity;
import com.example.MyApp.PropertyView.Domain.Ports.HotelPort;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.HotelEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.AddressEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.ContactsEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.ArrivalTimeEntity;
import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.AmenityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class HotelRepository implements HotelPort {

    private final JpaHotelRepository jpaHotelRepository;
    private final EntityManager entityManager;
    private final AmenityRepository amenityRepository;

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

    @Override
    public List<Hotel> searchHotels(HotelSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<HotelEntity> query = cb.createQuery(HotelEntity.class);
        Root<HotelEntity> root = query.from(HotelEntity.class);
        
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null) {
            predicates.add(cb.like(
                cb.lower(root.get("name")),
                "%" + criteria.getName().toLowerCase() + "%"
            ));
        }
        
        if (criteria.getBrand() != null) {
            predicates.add(cb.equal(root.get("brand"), criteria.getBrand()));
        }
        
        if (criteria.getCity() != null) {
            predicates.add(cb.equal(
                root.get("address").get("city"), 
                criteria.getCity()
            ));
        }
        
        if (criteria.getCounty() != null) {
            predicates.add(cb.equal(
                root.get("address").get("county"), 
                criteria.getCounty()
            ));
        }
        
        if (criteria.getAmenities() != null && !criteria.getAmenities().isEmpty()) {
            Join<HotelEntity, AmenityEntity> amenitiesJoin = root.join("amenities");
            predicates.add(amenitiesJoin.get("name").in(criteria.getAmenities()));
        }
        
        query.where(predicates.toArray(new Predicate[0])).distinct(true);
        
        return entityManager.createQuery(query)
            .getResultStream()
            .map(this::mapToDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getBrandHistogram(List<String> filterValues) {
        return getSimpleHistogram("brand", filterValues);
    }

    private Map<String, Long> getSimpleHistogram(String field, List<String> filterValues) {
        String jpql = """
            SELECT h.${field}, COUNT(h) 
            FROM HotelEntity h 
            ${whereClause}
            GROUP BY h.${field}
            """;
        
        String whereClause = filterValues != null && !filterValues.isEmpty() 
            ? "WHERE h.${field} IN :values" 
            : "";
        
        Query query = entityManager.createQuery(
            jpql
                .replace("${field}", field)
                .replace("${whereClause}", whereClause)
        );
        
        if (filterValues != null && !filterValues.isEmpty()) {
            query.setParameter("values", filterValues);
        }
        
        List<Object[]> results = query.getResultList();
        return results.stream()
            .collect(Collectors.toMap(
                arr -> (String) arr[0],
                arr -> (Long) arr[1]
            ));
    }

    @Override
    public Map<String, Long> getAmenitiesHistogram(List<String> filterValues) {
        String jpql = """
            SELECT a.name, COUNT(h) 
            FROM HotelEntity h 
            JOIN h.amenities a 
            ${whereClause}
            GROUP BY a.name
            """;
        
        String whereClause = filterValues != null && !filterValues.isEmpty() 
            ? "WHERE a.name IN :values" 
            : "";
        
        Query query = entityManager.createQuery(
            jpql.replace("${whereClause}", whereClause)
        );
        
        if (filterValues != null && !filterValues.isEmpty()) {
            query.setParameter("values", filterValues);
        }
        
        List<Object[]> results = query.getResultList();
        Map<String, Long> histogram = results.stream()
            .collect(Collectors.toMap(
                arr -> (String) arr[0],
                arr -> (Long) arr[1]
            ));
        
        if (filterValues != null) {
            filterValues.forEach(value -> 
                histogram.putIfAbsent(value, 0L)
            );
        }
        
        return histogram;
    }
    @Override
    public Map<String, Long> getCityHistogram(List<String> filterValues) {
        return getSimpleHistogram("address.city", filterValues);
    }

    @Override
    public Map<String, Long> getCountyHistogram(List<String> filterValues) {
        return getSimpleHistogram("address.county", filterValues);
    }

    @Transactional
    @Override
    public Hotel saveHotel(Hotel hotel) {
        HotelEntity entity = mapToEntity(hotel);
        HotelEntity savedEntity = jpaHotelRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    private HotelEntity mapToEntity(Hotel hotel) {
        HotelEntity entity = new HotelEntity();
        entity.setId(hotel.getId());
        entity.setName(hotel.getName());
        entity.setDescription(hotel.getDescription());
        entity.setBrand(hotel.getBrand());

        if (hotel.getAddress() != null) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setHouseNumber(hotel.getAddress().getHouseNumber());
            addressEntity.setStreet(hotel.getAddress().getStreet());
            addressEntity.setCity(hotel.getAddress().getCity());
            addressEntity.setCounty(hotel.getAddress().getCounty());
            addressEntity.setPostCode(hotel.getAddress().getPostCode());
            entity.setAddress(addressEntity);
        }

        if (hotel.getContacts() != null) {
            ContactsEntity contactsEntity = new ContactsEntity();
            contactsEntity.setPhone(hotel.getContacts().getPhone());
            contactsEntity.setEmail(hotel.getContacts().getEmail());
            entity.setContacts(contactsEntity);
        }

        if (hotel.getArrivalTime() != null) {
            ArrivalTimeEntity arrivalTimeEntity = new ArrivalTimeEntity();
            arrivalTimeEntity.setCheckIn(hotel.getArrivalTime().getCheckIn());
            arrivalTimeEntity.setCheckOut(hotel.getArrivalTime().getCheckOut());
            entity.setArrivalTime(arrivalTimeEntity);
        }

        if (hotel.getAmenities() != null) {
            List<AmenityEntity> amenityEntities = hotel.getAmenities().stream()
                .map(amenity -> {
                    Optional<AmenityEntity> existing = amenityRepository.findByName(amenity.getName());
                    return existing.orElseGet(() -> {
                        AmenityEntity newEntity = new AmenityEntity();
                        newEntity.setName(amenity.getName());
                        return amenityRepository.save(newEntity);
                    });
                })
                .collect(Collectors.toList());
            entity.setAmenities(amenityEntities);
        }
        
        return entity;
    }
}
