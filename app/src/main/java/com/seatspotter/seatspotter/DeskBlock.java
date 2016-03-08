package com.seatspotter.seatspotter;

public class DeskBlock {

    int id;
    int availDesks;
    int x;
    int y;
    int status;

    public DeskBlock (int id, int availDesks, int x, int y)
    {
        this.id = id;
        this.availDesks = availDesks;
        this.x = x;
        this.y = y;
        this.status = 2;
    }

    public int getStatus(){
        return this.status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getX(){
        return this.x;
    }

    public int getY() { return this. y; }
}

