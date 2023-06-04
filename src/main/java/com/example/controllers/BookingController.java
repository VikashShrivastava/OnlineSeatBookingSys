package com.example.controllers;

import com.example.CustException.CustException;
import com.example.customclasses.Custom;
import com.example.customclasses.Location;
import com.example.model.Booking;
import com.example.model.Seat;
import com.example.services.BookingService;
import com.example.services.BuildingService;
import com.example.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class   BookingController {
    private final BookingService bookingService;

    @Autowired
    private RestTemplate restTemplate;

    private final SeatService seatService;
    public BookingController(BookingService bookingService, SeatService seatService){
        this.bookingService = bookingService;
        this.seatService = seatService;
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<String> findByLocation(@RequestBody Location location){
        return bookingService.findByLocation(location);
    }
    @PostMapping("/bookseat")
    public ResponseEntity<String> bookseat(@RequestBody Custom custom) throws CustException, ParseException {
        System.out.println("aaaaaaaaaaaaaa");
        bookingService.bookseat(custom);
        System.out.println("bbbbbbbbbbbbb");
        return new ResponseEntity<>("Seat Booked Successfully and a mail regarding the same has been sent to you registered mail id", HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/cancelSeat")
    public ResponseEntity<String> cancelSeat(@RequestBody Custom custom) throws CustException
    {
        bookingService.cancelseat(custom);
        return new ResponseEntity<>("Booking Cancelled Successfully",HttpStatus.ACCEPTED);
    }
    @GetMapping("/getAllSeatsForTime")
    public ResponseEntity<String> getAllSeatsForTime(@RequestBody Custom custom) throws CustException, ParseException {
        bookingService.validateDates(custom);
        bookingService.validateTime(custom);
        List<Seat> seatList = seatService.getAllRoom();
        List<Booking> clasBookings = bookingService.getClashingSeats();
        List<Integer>clashSeatIds = new ArrayList<>();
        for(Booking b: clasBookings){
            for(Seat s: seatList){
                if(s.getSeatId() == b.getSeatId()){
                    if(bookingService.isClash(b.getStartDate(), b.getStartTime().substring(0,5), b.getEndTime().substring(0,5), b.getEndDate(), custom)){
                        clashSeatIds.add(s.getSeatId());
                    }
                }
            }
        }
        StringBuilder availableSeats = new StringBuilder();
        for(Seat s: seatList){
            if(!clashSeatIds.contains(s.getSeatId()))availableSeats.append(s.toString1()+"\n");
        }
        return new ResponseEntity<>(availableSeats.toString(), HttpStatus.FOUND);
    }

    @PostMapping("/getUserHistory")
    public String getUserHistory(@RequestBody Custom custom)throws CustException{
        System.out.println("ccccccc");
        String str = restTemplate.postForObject("http://localhost:8002/userController/login" , custom.getUser(), String.class);

        if(!str.equals("Welcome"))throw new CustException("No Such User Exist");
        System.out.println("ddddddd");
        String ans =  bookingService.getHistory(custom);

        System.out.println("ooooooooo");
        return ans;
    }
    @PostMapping("/bookNSeats")
    public ResponseEntity<String> bookNSeats(@RequestBody Custom custom) throws ParseException, CustException {
        List<Seat>seatList = seatService.getAllSeats(custom);
        if(seatList.size() < custom.getNumberOfSeats()){
            return new ResponseEntity<>("Enough number of seats are not available in given room.", HttpStatus.ACCEPTED);
        }
        StringBuilder content = new StringBuilder();
        for(int i = 0; i < custom.getNumberOfSeats(); i++){

            custom.getSeat().setSeatNo(seatList.get(i).getSeatNo());
            content.append(bookingService.bookNSeat(custom));
        }
        bookingService.sentEmail(custom.getUser(), content.toString());
        return new ResponseEntity<>("All Seat Booked Successfully and a mail regarding the same has been sent to you registered mail id", HttpStatus.ACCEPTED);
    }
}
