package com.example.controllers;


import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.services.FloorService;
import com.example.services.RoomService;
import com.example.services.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {
    private final RoomService roomService;
    private final FloorService floorService;
    private final SeatService seatService;

    public SeatController(RoomService roomService, FloorService floorService, SeatService seatService) {
        this.roomService = roomService;
        this.floorService = floorService;
        this.seatService = seatService;
    }

    @GetMapping("/listAllSeats")
    public ResponseEntity<String> listAllSeats(@RequestBody Custom custom)throws CustException {
        roomService.validateRoom(custom);
        return seatService.listAllSeats(custom);
    }
}

