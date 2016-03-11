package com.seatspotter.seatspotter;

public class Library {

    int id;
    String name;
    int totalDesks;
    int emptyDesks;
    int unknownState;

    public Library(int id, String name, int totalDesks, int emptyDesks, int unknownState){
        this.id = id;
        this.name = name;
        this.totalDesks = totalDesks;
        this.emptyDesks = emptyDesks;
        this.unknownState = unknownState;
    }
}
