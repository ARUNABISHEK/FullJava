package com.busbooking.data;

import com.busbooking.model.BusModel;
import com.busbooking.model.PassengerModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DataSet {
    static Date start_date;
    private final List<BusModel> buses = new ArrayList<>();

    private final List<PassengerModel> passengerList = new ArrayList<>();

    private Map<String, List<BusModel>> bus_date = new TreeMap<>();

    static {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar obj = Calendar.getInstance();
        String str = formatter.format(obj.getTime());
        try {
            start_date = new SimpleDateFormat("dd-MM-yyyy").parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Start Date: "+ start_date);
    }

    static DataSet data = null;
    public List<PassengerModel> getPassengerList() {
        return passengerList;
    }

    private DataSet() {}

    public static DataSet getObject() {
        if (data == null) {
            data = new DataSet();
        }
        return data;
    }

    public List<BusModel> getBuses() {
        return buses;
    }
    public Map<String, List<BusModel>> getBus_date() {
        return bus_date;
    }

    public void store(BusModel bus) {

        Date date;
        Map<String,List<BusModel>> map = new TreeMap<>();

        if(bus_date.size()==0) {
            for(int i=0;i<40;i++) {
                List<BusModel> b = new ArrayList<>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Calendar cal = Calendar.getInstance();

                cal.add(Calendar.DAY_OF_MONTH, i);
                String dateAfter = sdf.format(cal.getTime());


                try {
                    date = new SimpleDateFormat("dd-MM-yyyy").parse(dateAfter);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                bus.setTravel_date(date);

                BusModel bis = new BusModel(bus);
                b.add(bis);
                map.put(date.toString(),b);
            }
            bus_date = map;

        }
        else {
            for(int i=0;i<40;i++) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Calendar cal = Calendar.getInstance();

                cal.add(Calendar.DAY_OF_MONTH, i);
                String dateAfter = sdf.format(cal.getTime());


                try {
                    date = new SimpleDateFormat("dd-MM-yyyy").parse(dateAfter);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                BusModel b = new BusModel(bus);
                b.setTravel_date(date);
              bus_date.get(date.toString()).add(new BusModel(b));
            }

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
