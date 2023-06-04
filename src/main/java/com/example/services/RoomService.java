package com.example.services;


import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.model.Floor;
import com.example.model.Room;
import com.example.model.Seat;
import com.example.repository.RoomRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepo roomRepo;
    private final SeatService seatService;
    private final FloorService floorService;
    private final BuildingService buildingService;
    public RoomService(RoomRepo roomRepo, SeatService seatService, FloorService floorService, BuildingService buildingService) {
        this.roomRepo = roomRepo;
        this.seatService = seatService;
        this.floorService = floorService;
        this.buildingService = buildingService;
    }

    public boolean checkRoom(Integer roomNo, int floorNo, String buildingName) {
        Optional<Room> exist = roomRepo.checkRoom(roomNo, floorNo, buildingName);
        return !exist.isEmpty();
    }
    public ResponseEntity<String> addRoom(Custom custom){
        Room room = custom.getRoom();
        if(checkRoom(room.getRoomNo(), custom.getFloor().getFloorNo(),custom.getBuilding().getBuildingName()))return new ResponseEntity<>("Room Already Exist.", HttpStatus.ALREADY_REPORTED);
        Floor f = floorService.getFloor(custom).get();
        Room r = new Room(custom.getRoom().getRoomNo(), custom.getBuildingName(), custom.getRoom().getNumberOfSeats(), f);
        roomRepo.save(r);
        floorService.updateCapacity(custom.getBuildingName(), f.getFloorNo(), r.getNumberOfSeats());
        buildingService.updateCapacity(custom.getBuildingName(), r.getNumberOfSeats());
        Room r1 = roomRepo.getRoom(custom.getBuildingName(), f.getFloorNo(), room.getRoomNo()).get();
        int roomCapacity = room.getNumberOfSeats();
        for(int i = 0; i < roomCapacity; i++){
            Seat s = new Seat(i+1,f.getFloorNo(), custom.getBuildingName(), r1);
            seatService.addSeat(s);
        }
        String roomString = room.toString1() + "\nAdded Successfully.";
        return new ResponseEntity<>(roomString, HttpStatus.ACCEPTED);
    }

    public List<Room> getAllRooms(String buildingName, int floorNo) {
        return roomRepo.getAllRooms(buildingName, floorNo);
    }
    public boolean validateRoom(Custom custom)throws CustException {
        if(!floorService.verifyFloor(custom)){
            throw new CustException("No such floor exist.");
        }
        if(roomRepo.validateRoom(custom.getRoom().getRoomNo(), custom.getFloor().getFloorNo(), custom.getBuilding().getBuildingName()).isEmpty()){
            throw new CustException("No such room exist");
        }
        return true;
    }

}