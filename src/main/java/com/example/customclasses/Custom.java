package com.example.customclasses;

import com.example.model.*;

public class Custom {
    User user;
    Booking booking;
    Building building;
    Floor floor;
    Room room;
    Seat seat;
    int numberOfSeats;

    public Custom() {
    }

    public Custom(User user){
        this.user = user;
    }

    public Custom(User user, Booking booking, Building building, Floor floor, Room room, Seat seat, int numberOfSeats) {
        this.user = user;
        this.booking = booking;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.seat = seat;
        this.numberOfSeats = numberOfSeats;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getBuildingName() {
        return building.getBuildingName();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getBuildingLocation() {
        return building.getLocation();
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getUserPassword() {
        return user.getPassword();
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Custom{" +
                "user=" + user +
                ", booking=" + booking +
                ", building=" + building +
                ", floor=" + floor +
                '}';
    }
}
