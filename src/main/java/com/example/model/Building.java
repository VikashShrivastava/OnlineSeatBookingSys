package com.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Building {
    @Id
    private String buildingName;
    private String location;
    private int totalCapacity;

    public Building(String buildingName, String location, int totalCapacity) {
        this.buildingName = buildingName;
        this.location = location;
        this.totalCapacity = totalCapacity;
    }
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Floor>floorList;

    public Building() {

    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }


    public List<Floor> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingName='" + buildingName + '\'' +
                ", location='" + location + '\'' +
                ", totalCapacity=" + totalCapacity +
                ", floorList=" + floorList +
                '}';
    }
    public String toString1() {
        return "buildingName='" + buildingName + '\'' +
                ", location='" + location + '\'' +
                ", totalCapacity=" + totalCapacity +
                '.';
    }
}
