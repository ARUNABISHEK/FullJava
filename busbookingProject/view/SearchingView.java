package com.busbooking.view;

import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SearchingView {
    static Scanner sc = new Scanner(System.in);

    public static void thanksCard() {
        System.out.println("|---------------------------------------|");
        System.out.println("|Thank-you for using this application!!!|");
        System.out.println("|---------------------------------------|");
    }
    public static int menu() {      //Main menu
        System.out.println("---------------------");
        System.out.println("1.Admin login");
        System.out.println("2.Show Buses");
        System.out.println("3.Book Ticket");
        System.out.println("4.Show Ticket");
        System.out.println("5.Cancel Ticket");
        System.out.println("0.Exit");
        System.out.println("---------------------");
        System.out.print("Enter your choice : ");
        int choice = -1;
        try {
            choice = Integer.parseInt(sc.nextLine());
            return choice;
        } catch (Exception e) {}

        return choice;
    }


    public static int menu(String admin) {          //Admin menu

        System.out.println("----------------------");
        System.out.println("1.Add bus");
        System.out.println("2.list of buses");
        System.out.println("3.Update");
        System.out.println("0.Exit");
        System.out.println("----------------------");
        int ch = -1;
        try {
            ch = Integer.parseInt(sc.nextLine());
            return ch;
        }catch (Exception e){}
        return ch;
    }


    public void show() {

        System.out.print("From : ");
        String from = sc.nextLine().toLowerCase();
        System.out.print("To   : ");
        String to = sc.nextLine().toUpperCase();
        System.out.print("Date (DD-MM-YYYY) : ");
        String date = sc.nextLine();

        SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1, d2;
        try {
            d1 = new Date();   //current date
            d1.setHours(0);
            d1.setMinutes(0);
            d1.setSeconds(0);
            d2 = sdformat.parse(date);   //selected date
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (d1.compareTo(d2) < 0 || d1.toString().equals(d2.toString())) {
            if(! DataSet.getObject().getBus_date().containsKey(d2.toString())) {
                //System.out.println("Seleceted date : " + d2);
                //else {
                    try {
                        throw new Exception("Portal not opened");
                    } catch (Exception e) {
                        throw new RuntimeException("Oops...Portal not opened");
                    }
                //}
            }
        } else {
            try {
                throw new Exception("Invalid Date");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        DataSet d = DataSet.getObject();
        if(d.getBus_date().containsKey(d2.toString())) {
            List<BusModel> buses = d.getBus_date().get(d2.toString());
            for(BusModel bus : buses) {
                if (bus.isAvailable() && bus.getBoardings().containsValue(from)
                    && to.equalsIgnoreCase(bus.getArrive()) &&
                    DataSet.getObject().getBus_date().containsKey(d2.toString())) {
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println("Seleceted date : " + d2);
                    System.out.println("No\t\tstarting_location\tarrive\t\tavailableseet");
                    System.out.println(bus.toString());
                    System.out.println("Boarding location and time");
                    System.out.println(from + "(" + bus.getBoardingTime().get(from).getHours() + ":" + bus.getBoardingTime().get(from).getMinutes() + ")");
                    System.out.println("-----------------------------------------------------------------");
                }
            }
        }
        else {
            System.err.println("Sorry...bus not available");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void listOfBuses() {
        Iterator it = DataSet.getObject().getBuses().listIterator();
        boolean bool = false;
        System.out.println("---------------------------------------------------------");
        while(it.hasNext()) {
            bool = true;
            System.out.println(it.next().toString());
        }
        if(!bool)
            System.out.println("Bus not available...");
    }

    static SearchingView search = null;
    private SearchingView() {}

    public static SearchingView getObject() {
        if(search==null) {
            search = new SearchingView();
        }
        return search;
    }
}
