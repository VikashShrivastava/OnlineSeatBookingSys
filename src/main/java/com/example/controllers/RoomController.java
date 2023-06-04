package com.example.controllers;


import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.model.Room;
import com.example.services.BuildingService;
import com.example.services.FloorService;
import com.example.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RoomController {
   @Autowired
   private RestTemplate restTemplate;
    private final BuildingService buildingService;
    private final FloorService floorService;
    private final RoomService roomService;
    public RoomController(BuildingService buildingService, FloorService floorService, RoomService roomService){

        this.buildingService = buildingService;
        this.floorService = floorService;
        this.roomService = roomService;
    }

    @PostMapping("/addNewRoom")
    public ResponseEntity<String> addNewRoom(@RequestBody Custom custom) throws CustException {
        String str = restTemplate.postForObject("http://localhost:8002/userController/verifyAdmin" ,custom.getUser(), String.class);

        if(!str.equals("Welcome"))throw new CustException("No Such User Exist");

        buildingService.verifyBuilding(custom.getBuildingName());
        if(!floorService.verifyFloor(custom))throw new CustException("No such floor exist");
        return roomService.addRoom(custom);
    }
    @GetMapping("/listAllRooms")
    public ResponseEntity<String> ListAllRooms(@RequestBody Custom custom) throws CustException {
        String str = restTemplate.postForObject("http://localhost:8002/userController/login" , custom.getUser(), String.class);
        if(!str.equals("Welcome"))throw new CustException("No Such User Exist");

        buildingService.verifyBuilding(custom.getBuilding().getBuildingName());
        floorService.verifyFloor(custom);
        List<Room> roomList =  roomService.getAllRooms(custom.getBuilding().getBuildingName(), custom.getFloor().getFloorNo());
        StringBuilder rooms =  new StringBuilder();
        rooms.append("Rooms Available:-\n");
        for(Room r: roomList){
            rooms.append(r.toString1()).append("\n");
        }
        return new ResponseEntity<>(rooms.toString(), HttpStatus.FOUND);
    }
}
