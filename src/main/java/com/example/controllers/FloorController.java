package com.example.controllers;

import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.model.Building;
import com.example.model.Floor;
import com.example.services.BuildingService;
import com.example.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class FloorController {

    private final FloorService floorService;
    @Autowired
    private RestTemplate restTemplate;
    private final BuildingService buildingService;

    public FloorController(FloorService floorService, BuildingService buildingService) {
        this.floorService = floorService;
        this.buildingService = buildingService;

    }

    @PostMapping("/addFloor")
    public ResponseEntity<String> addFloor(@RequestBody Custom custom) throws CustException {
        System.out.println(custom);
        //verify user.
        String str = restTemplate.postForObject("http://localhost:8002/userController/verifyAdmin" , custom.getUser(), String.class);

        if(!str.equals("Welcome"))throw new CustException("No Such User Exist");
        //verifyBuilding
        if(!buildingService.verifyBuilding(custom.getBuildingName()))throw new CustException("No Such Building Exist");
        Optional<Floor> fl =  floorService.getFloor(custom);
        if(fl.isPresent())throw new CustException("Floor already Exist");
        Building b = buildingService.getBuilding(custom.getBuilding());
        Floor newf = new Floor(custom.getFloor().getFloorNo(), custom.getFloor().getFloorCapacity(),b);
        floorService.addFloor(newf);
        return new ResponseEntity<>("Floor in corresponding Building Added successfully", HttpStatus.ACCEPTED);
    }
    @GetMapping("/findByFloor")
    public ResponseEntity<String> findByFloor(@RequestBody Custom custom) throws CustException{
        //verifyBuilding
        if(!buildingService.verifyBuilding(custom.getBuildingName()))throw new CustException("No Such Building Exist");
        List<Floor> floorList = floorService.listAllFloors(custom.getBuildingName());
        StringBuilder floors = new StringBuilder();
        floors.append("Floors Registered:\n");
        for(Floor f: floorList){
            floors.append(f.toString1()).append("\n");
        }
        return new ResponseEntity<>(floors.toString(), HttpStatus.FOUND);
    }
}

