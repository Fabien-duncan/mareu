package com.example.mareu.models;

public class Room {
    private int roomNumber;
    private int maxSize;

    public Room(int roomNumber, int maxSize) {
        this.roomNumber = roomNumber;
        this.maxSize = maxSize;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
