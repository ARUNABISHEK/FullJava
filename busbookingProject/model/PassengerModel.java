package com.busbooking.model;

import java.util.Date;

public class PassengerModel {

    private static int id = 0;
    private String name;
    private int seatNo;
    private String from;
    private String to;
    private long mobile;
    //private String mail = null;
    private char gender;
    private int ticket_number;
    private int bus_number;
    private Date travel_date;

    public int getBus_number() {
        return bus_number;
    }

    public void setBus_number(int bus_number) {
        this.bus_number = bus_number;
    }

    public int getTicket_number() {
        return ticket_number;
    }

    public void setTicket_number(int ticket_number) {
        this.ticket_number = ticket_number;
    }

    public Date getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(Date travel_date) {
        this.travel_date = travel_date;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        PassengerModel.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

//    public String getMail() {
//        return mail;
//    }
//
//    public void setMail(String mail) {
//        this.mail = mail;
//    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

}
