package com.seatspotter.seatspotter;

public class Floor {

    String floorName;
    int floorNumber;
    Desk[] desks;

    public Floor(String floorName, int floorNumber, Desk[] desks){
        this.floorName = floorName;
        this.floorNumber = floorNumber;
        this.desks = desks;
    }
}
