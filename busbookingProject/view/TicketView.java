package com.busbooking.view;

import com.busbooking.controller.Sleep;
import com.busbooking.model.BusModel;
import com.busbooking.model.SeatModel;
import com.busbooking.data.DataSet;
import com.busbooking.model.PassengerModel;

import java.util.*;

public class TicketView {

    private static TicketView obj = null;
    private TicketView(){}

    public static TicketView getObject() {
        if(obj==null)
            obj = new TicketView();
        return obj;
    }

    public void show(int no) {
        boolean bool = false;
        for(PassengerModel passenger : DataSet.getObject().getPassengerList()) {
            if(passenger.getTicket_number() == no) {
                bool = true;
                System.out.println("----------------------------------------------------------------------");
                System.out.println("Ticket Number : "+ passenger.getTicket_number() + "\t Name :" + passenger.getName());
                System.out.println("Date : " + passenger.getTravel_date());
                System.out.println("Bus Number : "+ passenger.getBus_number() + "\t Seat Number : " + passenger.getSeatNo() + "\t Gender : " + passenger.getGender());
                System.out.println("From : "+ passenger.getFrom() + "\t To : " + passenger.getTo());
                System.out.println("Mobile : " + passenger.getMobile());
                System.out.println("----------------------------------------------------------------------");
            }
        }
        if(!bool) {
            System.err.println("Ticket not found....");
            Sleep.sleep();
        }

    }

    public void delete(int no, Date d) {
        Scanner sc = new Scanner(System.in);
        boolean bool = false;
        ListIterator<PassengerModel> itr = DataSet.getObject().getPassengerList().listIterator();

        while (itr.hasNext()) {
            PassengerModel passenger = itr.next();
            if(passenger.getTicket_number() == no) {

                System.out.println("Conform (1/0): ");
                int ok = sc.nextInt();
                if(ok==1) {

                    List<BusModel> bus =  DataSet.getObject().getBus_date().get(d.toString());
                    if(bus == null) {
                        System.err.println("Wrong Date...");
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    ListIterator<BusModel> iter = bus.listIterator();

                    while (iter.hasNext()) {
                        BusModel b = iter.next();

                        if (passenger.getBus_number() == b.getNumber() && passenger.getTravel_date().toString().equals(b.getTravel_date().toString())) {
                            SeatModel[] seat = b.getSeat();
                            seat[passenger.getSeatNo() - 1].setAvailable(true);
                            b.setSeat(seat);
                            itr.remove();
                            bool = true;
                            System.out.println("Deleted successfully...");
                        }
                    }

                }
                else
                    return;
            }
        }
        if(!bool) {
            System.err.println("Ticket Not found");
            Sleep.sleep();
        }
    }

}
