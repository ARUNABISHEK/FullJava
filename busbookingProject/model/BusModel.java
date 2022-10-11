package com.busbooking.model;

import java.util.*;

public class BusModel {
    private int capacity;
    private int number;
    private boolean available = true;
    private Date departTime;
    private Date arriveTime;
    private SeatModel[] seat;
    private String depart;
    private String arrive;
    private Map<Integer,String> boardingPoints;
    private Map<String,Date> boardingTime;
    private int seater;
    private int lwrs;
    private int uprs;

    private Date travel_date;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getTravel_date() {
        return travel_date;
    }
    public void setTravel_date(Date travel_date) {
        this.travel_date = travel_date;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Map<Integer, String> getBoardingPoints() {
        return boardingPoints;
    }

    public int getSeater() {
        return seater;
    }

    public void setSeater(int seater) {
        this.seater = seater;
    }

    public int getLwrs() {
        return lwrs;
    }

    public void setLwrs(int lwrs) {
        this.lwrs = lwrs;
    }

    public int getUprs() {
        return uprs;
    }

    public void setUprs(int uprs) {
        this.uprs = uprs;
    }

    public Map<String, Date> getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Map<String, Date> boardingTime) {
        this.boardingTime = boardingTime;
    }

    public Map<Integer, String> getBoardings() {
        return boardingPoints;
    }

    public void setBoardings(Map<Integer, String> boardings) {
        this.boardingPoints = boardings;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public SeatModel[] getSeat() {
        return seat;
    }

    public void setSeat(SeatModel[] seat) {
        this.seat = seat;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public BusModel(){}
    public BusModel(BusModel copy_bus) {
        this.number = copy_bus.getNumber();
        this.departTime = copy_bus.getDepartTime();
        this.arriveTime = copy_bus.getArriveTime();
        this.depart = copy_bus.getDepart();
        this.arrive = copy_bus.getArrive();
        this.boardingPoints = copy_bus.getBoardingPoints();
        this.boardingTime = copy_bus.getBoardingTime();
        this.seater = copy_bus.getSeater();
        this.lwrs = copy_bus.getLwrs();
        this.uprs = copy_bus.getUprs();
        this.travel_date = copy_bus.getTravel_date();
        this.seat = new SeatModel[seater + lwrs + uprs];
        for(int i = 0; i< seat.length; i++) {
            this.seat[i] = new SeatModel();
            seat[i].setSeatNumber(i+1);
        }
    }
    public int availableseet() {
        int count = 0;
        for(int i = 0; i<this.seat.length; i++) {
            if(!this.seat[i].isAvailable()) {
                count++;

            }
        }

        return this.getSeat().length - count;
    }

    @Override
    public String toString() {
        return this.number + " : " +
                this.depart + " (" + this.departTime.getHours() +":" + this.departTime.getMinutes() + ") :\t " +
                this.arrive + " (" +  this.arriveTime.getHours()+":"+this.arriveTime.getMinutes() + ") " + " :\t " +
                this.availableseet();
    }



}
