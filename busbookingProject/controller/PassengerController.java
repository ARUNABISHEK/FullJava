package com.busbooking.controller;

import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;
import com.busbooking.model.PassengerModel;
import com.busbooking.model.SeatModel;
import com.busbooking.view.PassengerView;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PassengerController {

    private static int ticketCounter = 1000;
    public void book(int bus_no, String date) {
        PassengerModel passenger;
        //boolean flag = false;
        try {
            passenger = new PassengerView().book(bus_no,date);
            //flag = true;
            if(passenger==null)
                return;
        } catch (Exception e) {
            System.err.println("Seat Not Available");
            Sleep.sleep();
            return;
        }

        for(Map.Entry<String, List<BusModel>> bus_date : DataSet.getObject().getBus_date().entrySet()) {
            if (bus_date.getKey().equals(passenger.getTravel_date().toString())) {
                for (BusModel bus : bus_date.getValue()) {
                    if (bus.getNumber() == bus_no) {
                        if (bus.availableseet() > 0) {
                            Date d = new Date();
                            if(passenger.getTravel_date().getDate()!=d.getDate()  || (passenger.getTravel_date().getDate()==d.getDate() && (bus.getDepartTime().getHours()-2 > d.getHours()))) { //time limit
                                for (SeatModel seat : bus.getSeat()) {

                                    if (seat.getSeatNumber() == passenger.getSeatNo()) {
                                        if (seat.isAvailable()) {
                                            seat.setAvailable(false);
                                            seat.setGenderOccupancy(passenger.getGender());
                                            seat.setPassenger(passenger);
                                            seat.setTicketNo(ticketCounter++);

                                            System.out.println("Ticket No : " + ticketCounter);
                                            passenger.setTicket_number(ticketCounter);
                                            passenger.setBus_number(bus.getNumber());
                                            DataSet.getObject().getPassengerList().add(passenger);
                                            System.out.println("Booked successfully");
                                        } else {
                                            System.err.println("Already booked...");
                                        }

                                    }
                                }
                            }
                            else {
                                System.err.println("Time limit exited...bus start in " + bus.getDepartTime());
                                Sleep.sleep();
                            }
                        } else {
                            System.err.println("Seat not available");
                            Sleep.sleep();
                        }
                    }
                }
            }
        }
    }
}
