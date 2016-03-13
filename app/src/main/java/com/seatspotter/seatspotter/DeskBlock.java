package com.seatspotter.seatspotter;

public class DeskBlock {

    int id;
    int emptyDesks;
    int x;
    int y;
    int xLength;
    int yLength;
    int floorID;
    int totalDesks;
    int unknownState;

    public DeskBlock (int id, int emptyDesks, int x, int y, int xLength, int yLength, int floorID, int totalDesks, int unknownState)
    {
        this.id = id;
        this.emptyDesks = emptyDesks;
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
        this.floorID = floorID;
        this.totalDesks = totalDesks;
        this.unknownState = unknownState;
    }
}

