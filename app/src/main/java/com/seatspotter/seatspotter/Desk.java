package com.seatspotter.seatspotter;

public class Desk {

    int id;
    int blockID;
    int deskState;
    int x;
    int y;
    int xLength;
    int yLength;

    public Desk(int id, int blockID, int deskState, int x, int y, int xLength, int yLength )
    {
        this.id = id;
        this.blockID = blockID;
        this.deskState = deskState;
        this.x = x;
        this.y = y;
        this.xLength = xLength;
        this.yLength = yLength;
    }
}
