package com.busbooking.view;

import com.busbooking.controller.Sleep;
import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;
import com.busbooking.model.PassengerModel;
import com.busbooking.model.SeatModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerView {

    public PassengerModel book(int bus_no , String date) {

        Scanner sc = new Scanner(System.in);

        PassengerModel p = new PassengerModel();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date day;

        try {
            day = dateformat.parse(date);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Name : ");
        String name = sc.nextLine();

        System.out.print("Gender M/F : ");
        char gender = sc.nextLine().toUpperCase().charAt(0);

        System.out.print("Seat No : ");
        int seatNo = 0;
        try {
            seatNo = sc.nextInt();
            sc.nextLine();

        }catch (Exception e) {
            try {
                throw new Exception("Invalid seat number");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        for(Map.Entry<String, List<BusModel>> bus_date : DataSet.getObject().getBus_date().entrySet()) {
            if (bus_date.getKey().equals(day.toString())) {
                for (BusModel bus : bus_date.getValue()) {
                    if(seatNo <= bus.getCapacity()) {
                        System.out.println("Wrong seat number...");
                        return null;
                    }
                    if (bus.getNumber() == bus_no) {
                        for (Map.Entry<Integer, String> map : bus.getBoardings().entrySet()) {
                            System.out.println("* - " + map.getValue());
                        }
                    }
                }
            }
        }

        System.out.print("From : ");
        String from = sc.nextLine().toLowerCase();

        System.out.print("To : ");
        String to = sc.nextLine().toLowerCase();

        for(Map.Entry<String, List<BusModel>> bus_date : DataSet.getObject().getBus_date().entrySet()) {
            if (bus_date.getKey().equals(day.toString())) {
                for (BusModel bus : bus_date.getValue()) {
                    if (bus.getNumber() == bus_no) {
                        /*
                        if(bus.getBoardings().containsValue(to)) {
                                for(SeatModel seat : bus.getSeat()) {
                                    PassengerModel passenger = seat.getPassenger();
                                    System.out.println(passenger.getTo());
                                    System.out.println(bus.getArrive());
                                    if(passenger.getFrom().equals(to) && passenger.getTo().equals(bus.getArrive())) {
                                        System.out.print("Mobile : ");
                                        long mobile = sc.nextLong();
                                        p.setName(name);
                                        p.setGender(gender);
                                        p.setMobile(mobile);
                                        p.setFrom(from);
                                        p.setTo(to);
                                        p.setTravel_date(day);
                                        System.out.println(seat.getSeatNumber() + " is allotted");
                                        p.setSeetNo(seat.getSeatNumber());

                                        return p;
                                    }
                                }
                        }
                        */

                        if(! bus.getBoardings().containsValue(from)) {
                            System.out.println("Location not found");
                            return null;
                        }
                        else if (! bus.getArrive().equals(to)) {
                            System.out.println("Only " + bus.getArrive() + " location.");
                            return null;
                        }
                    }
                }
            }
        }



        if(! DataSet.getObject().getBus_date().containsKey(day.toString())) {
            try {
                throw new Exception("Portal not created");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("Mobile : ");
        long mobile = sc.nextLong();

        Pattern ptrn = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher match = ptrn.matcher(String.valueOf(mobile));

        if(! match.find()){
            System.out.println("Wrong mobile number...");
            return null;
        }

        p.setName(name);
        p.setGender(gender);
        p.setMobile(mobile);
        p.setFrom(from);
        p.setTo(to);
        p.setTravel_date(day);
        p.setSeatNo(seatNo);

        return p;
    }

    public static void showPassengerList(int bus_no,Date d) {

        if(DataSet.getObject().getBuses().size() == 0) {
            System.err.println("Bus Not available");
            Sleep.sleep();
        }
        else {
            boolean flag = false;

            for (BusModel bus : DataSet.getObject().getBuses()) {
                if (bus.getNumber() == bus_no) {
                    for (SeatModel seats : bus.getSeat()) {
                        PassengerModel passenger = seats.getPassenger();
                        if (passenger != null) {
                            flag = true;
                            System.out.println(passenger.getSeatNo() + " : " + passenger.getName() + " : " +
                                    passenger.getGender() + " : " + passenger.getMobile() + " : " + passenger.getTravel_date());
                        }

                    }
                }
            }
            if(!flag) {
                System.err.println("Empty...");
                Sleep.sleep();
            }
        }
    }
}
