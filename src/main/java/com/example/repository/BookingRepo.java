package com.example.repository;


import com.example.model.Booking;
import com.example.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    @Query(value = "select u from Booking u where u.buildingName=?1 and u.startDate>=?2")
    List<Booking> getBookinglist(String buildingName, String d) ;
    @Query(value = "select b from Building b where b.location = ?1")
    Optional<List<Building>> findAvailableSeatsAtLocation(String location);
    @Query(value = "select u from Booking u where u.buildingName=?1 and u.startDate>=?2 and u.endDate<=?3 and u.seatId=?4")
    List<Booking> getBookinglist1(String buildingName, String date1, String date2, Integer seatId);
    @Query(value = "select b from Booking b ")
    List<Booking> getClashingSeats();
    @Query(value = "select b from Booking b where b.userId = ?1")
    Optional<List<Booking>> getUserHistory(String userId);
}
