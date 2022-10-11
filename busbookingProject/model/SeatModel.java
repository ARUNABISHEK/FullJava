package com.busbooking.model;

public class SeatModel {

    private int seatNumber;
    private PassengerModel passenger;
    private char genderOccupancy;
    private boolean available = true;
    private char restrictBook;

    private int ticketNo;

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public char getRestrictBook() {
        return restrictBook;
    }

    public void setRestrictBook(char restrictBook) {
        this.restrictBook = restrictBook;
    }
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public PassengerModel getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerModel passenger) {
        this.passenger = passenger;
    }

    public char getGenderOccupancy() {
        return genderOccupancy;
    }

    public void setGenderOccupancy(char genderOccupancy) {
        this.genderOccupancy = genderOccupancy;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean availableSeats) {
        this.available = availableSeats;
    }

}
