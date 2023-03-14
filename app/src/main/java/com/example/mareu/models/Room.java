package com.example.mareu.models;

/**
 * Class used to create rooms.
 * contains room numbers and maximum number of people per room
 */
public class Room {
    private int roomNumber;
    private int maxSize;

    /**
     * constructor
     * @param roomNumber
     * @param maxSize
     */
    public Room(int roomNumber, int maxSize) {
        this.roomNumber = roomNumber;
        this.maxSize = maxSize;
    }

    /**
     * @return room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * sets room number
     * @param roomNumber
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
