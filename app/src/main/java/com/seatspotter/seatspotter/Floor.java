package com.seatspotter.seatspotter;

public class Floor {

    int id;
    String floorLevel;
    int totalDesks;
    int emptyDesks;
    int unknownState;

    public Floor(int id, String floorLevel, int totalDesks, int emptyDesks, int unknownState){
        this.id = id;
        this.floorLevel = floorLevel;
        this.totalDesks = totalDesks;
        this.emptyDesks = emptyDesks;
        this.unknownState = unknownState;
    }
}
