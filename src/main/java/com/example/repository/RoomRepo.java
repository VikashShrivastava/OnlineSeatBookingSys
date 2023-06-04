package com.example.repository;


import com.example.model.Room;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    @Query(value = "select r  from Room r where r.roomNo = ?1 and r.floor.floorNo = ?2 and r.buildingName = ?3")
    Optional<Room> checkRoom(Integer roomNo, Integer floorNo, String buildingName);
    @Query(value = "select r from Room  r where r.buildingName= ?1 and r.floor.floorNo = ?2 ")
    List<Room> getAllRooms(String buildingName, Integer floorNo);
    @Query(value = "select r from Room r where r.buildingName = ?1 and r.floor.floorNo = ?2 and r.roomNo = ?3")
    Optional<Room> getRoom(String buildingName, Integer floorNo, Integer roomNo);
    @Query(value = "select r from Room r where r.roomNo = ?1 and r.floor.floorNo = ?2 and r.buildingName = ?3")
    Optional<Room> validateRoom(Integer roomNo, Integer floorNo, String buildingName);
}
