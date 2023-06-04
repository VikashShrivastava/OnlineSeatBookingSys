package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "floor")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long floorId;
    private Integer floorNo;
    private int floorCapacity;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="buildingName")
    Building building;
    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Room>roomList;

    public Floor(Integer floorNo, int floorCapacity, Building building) {
        this.floorNo = floorNo;
        this.floorCapacity = floorCapacity;
        this.building = building;
    }

    public Floor() {

    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorNo=" + floorNo +
                ", floorCapacity=" + floorCapacity +
                '}';
    }

    public String toString1() {
        return "floorNo=" + floorNo +
                ", floorCapacity=" + floorCapacity +
                '.';
    }

    public Integer getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(Integer floorNo) {
        this.floorNo = floorNo;
    }

    public int getFloorCapacity() {
        return floorCapacity;
    }

    public void setFloorCapacity(int floorCapacity) {
        this.floorCapacity = floorCapacity;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Long getFloorId() {
        return floorId;
    }
}

