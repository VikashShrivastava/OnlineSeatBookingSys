package com.example.repository;



import com.example.model.Building;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepo extends JpaRepository<Building, String> {
    @Modifying
    @Transactional
    @Query(value = "update Building b set b.totalCapacity = b.totalCapacity+?2 where b.buildingName = ?1")
    void updateCapacity(String buildingName, int numberOfSeats);
}