package com.example.controllers;

import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.model.Building;
import com.example.model.User;
import com.example.services.BookingService;
import com.example.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BuildingController {

    private final BuildingService buildingService;
    @Autowired
    private RestTemplate restTemplate;
    private final BookingService bookingService;

    public BuildingController(BuildingService buildingService,BookingService bookingService) {
        this.buildingService = buildingService;
        this.bookingService = bookingService;
    }

    @PostMapping("/addBuilding")
    public ResponseEntity<String> addBuilding(@RequestBody Custom custom) throws CustException {
        //verifyUser
        System.out.println(custom+"\n\n\n");
        System.out.println(custom.getUser());
        String str = restTemplate.postForObject("http://localhost:8002/userController/verifyAdmin", custom.getUser(), String.class);

        if(!str.equals("Welcome"))
        {
            return new ResponseEntity<>("No such admin exits",HttpStatus.FORBIDDEN);
        }

        //user verified, now adding building

        buildingService.addBuilding(custom.getBuilding());

        return new ResponseEntity<>("Bravo...you added a new building",HttpStatus.ACCEPTED);
    }
    @GetMapping("/findByBuilding")
    public ResponseEntity<String> findByBuilding(@RequestBody Custom custom) throws CustException {
        String str = restTemplate.postForObject("http://localhost:8002/userController/verifyAdmin", custom.getUser(), String.class);

        if(!str.equals("Welcome")){
            return new ResponseEntity<>("No such admin exits.", HttpStatus.BAD_REQUEST);
        }
        return buildingService.findByBuilding(custom);
    }
    @DeleteMapping("/deletebuilding")
    public ResponseEntity<String> deleteBuilding(@RequestBody Custom custom) throws CustException {

        String str = restTemplate.postForObject("http://localhost:8002/userController/verifyAdmin" , custom.getUser(), String.class);

        if(!str.equals("Welcome")){
            return new ResponseEntity<>("No such admin exits.", HttpStatus.BAD_REQUEST);
        }

        buildingService.deletebuilding(custom.getBuilding());
        return new ResponseEntity<>("Building Deleted Successfully",HttpStatus.ACCEPTED);
    }
}
