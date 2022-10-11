package com.busbooking.controller;

import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;
import com.busbooking.view.BusView;
import com.busbooking.view.SeatView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BusController {

    public void addBus() {
        BusModel bus_model = new BusView().addBus();
        if(bus_model==null) {
            System.err.println("-----Enter valid information-----");
            Sleep.sleep();
            return;
        }
        DataSet data = DataSet.getObject();
        data.getBuses().add(bus_model);
        DataSet.getObject().store(bus_model);
    }
    public static void book() {
        Scanner sc = new Scanner(System.in);
        if(DataSet.getObject().getBus_date().size()==0) {
            System.out.println("Bus not available");
            return;
        }

        System.out.println("How many Ticket book : ");
        int total;
        try {
            total = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Sleep.sleep();
            return;
        }
        if(total>57) {
            System.err.println("Seats not available...");
            Sleep.sleep();
            return;
        }

        System.out.print("Bus number : ");

        int busNo;
        try {
            busNo = Integer.parseInt(sc.nextLine());

        }catch (Exception e){
            System.err.println("Only numbers");
            Sleep.sleep();
            return;
        }

        System.out.print("Date(dd-MM-yyyy) : ");
        String date = sc.nextLine();
        Date day;
        try {
            day = new SimpleDateFormat("dd-MM-yyyy").parse(date);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            Sleep.sleep();
            return;
        }

        //check availability
        for(Map.Entry<String, List<BusModel>> bus_date : DataSet.getObject().getBus_date().entrySet()) {
            if (bus_date.getKey().equals(day.toString())) {
                for (BusModel bus : bus_date.getValue()) {
                    if (bus.getNumber() == busNo) {
                        if(total > bus.availableseet()) {
                            System.out.println("Only " + bus.availableseet() + " Seats left");
                            return;
                        }
                    }
                }
            }
        }

        for(int i=0;i<total;i++) {
            boolean bool = SeatView.show(busNo, day);
            if (bool) {
                PassengerController passenger = new PassengerController();
                passenger.book(busNo, date);
            } else {
                System.err.println("Check bus number...");
                Sleep.sleep();
                break;
            }
        }
    }

}