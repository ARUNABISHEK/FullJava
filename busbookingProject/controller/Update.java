package com.busbooking.controller;

import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;
import com.busbooking.view.TicketView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Update {

    static Scanner sc = new Scanner(System.in);
    @SuppressWarnings("deprecation")
    public static void update() {
        if(DataSet.getObject().getBus_date().size()==0) {
            System.out.println("Bus not available");
            return;
        }
        Date date_after_40;

        Date now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setDate(now.getDate());

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar obj = Calendar.getInstance();
        obj.add(Calendar.DAY_OF_MONTH, 40);
        String str = formatter.format(obj.getTime());
        try {
            date_after_40 = new SimpleDateFormat("dd-MM-yyyy").parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        DataSet d = DataSet.getObject();


        List<BusModel> busModels = d.getBus_date().get(now.toString());

        d.getBus_date().put(date_after_40.toString(),busModels);
        d.getBus_date().remove(now.toString());
        System.out.println("Removed : " + now);
        System.out.println("Added : " + date_after_40);
        System.out.println("Updated...");
    }

    public static void view() {
        System.out.print("Enter Ticket No : ");
        int ticketNo = sc.nextInt();sc.nextLine();
        TicketView ticket = TicketView.getObject();
        ticket.show(ticketNo);
    }

    public static void delete() {

        System.out.print("Enter Ticket No : ");
        int ticketNo = sc.nextInt();sc.nextLine();
        System.out.print("Date : ");
        String date1 = sc.nextLine();
        Date d;
        try {
            d = new SimpleDateFormat("dd-MM-yyyy").parse(date1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        TicketView ticket = TicketView.getObject();
        ticket.delete(ticketNo,d);
    }
}
