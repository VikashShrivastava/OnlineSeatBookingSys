package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_table")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer seatId;
    private Integer seatNo;
    private int floorNo;

    public Seat(Integer seatNo, int floorNo, String buildingName, Room room) {
        this.seatNo = seatNo;
        this.floorNo = floorNo;
        this.buildingName = buildingName;
        this.room = room;
    }

    private String buildingName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "roomNo")
    Room room;

    public Seat() {

    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNo=" + seatNo +
                ", floorNo=" + floorNo +
                ", buildingName='" + buildingName + '\'' +
                ", room=" + room +
                '}';
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public String toString1() {
        return "seatNo=" + seatNo +
                ", floorNo=" + floorNo +
                ", buildingName='" + buildingName + '\'' +
                ", room No:"+ room.getRoomNo()+
                ".";
    }
}
