package com.example.MyApp.PropertyView.Infrastructure.Persistence.Repository;

import com.example.MyApp.PropertyView.Infrastructure.Persistence.Entity.AmenityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<AmenityEntity, Long> {
    Optional<AmenityEntity> findByName(String name);
}
