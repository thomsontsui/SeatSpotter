package com.seatspotter.seatspotter;

public class Desk {

    int id;
    int block;
    int x;
    int y;
    int status;

    public Desk(int id, int block, int x, int y)
    {
        this.id = id;
        this.block = block;
        this.x = x;
        this.y = y;
        this.status = 0;
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
