package com.busbooking.view;

import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;
import com.busbooking.model.SeatModel;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BusView {

    public BusModel addBus() {
        Scanner sc = new Scanner(System.in);
        BusModel b = new BusModel();
        //Add bus
        int sitting,lwrb,uprb;
        System.out.print("Number of Seating's(0/20/42) : ");
        try {
            sitting = Integer.parseInt(sc.nextLine());
        }catch (Exception e){ return null;}

        System.out.print("Number of lower Berth(15/5/0) : ");
        try {
            lwrb = Integer.parseInt(sc.nextLine());
        }catch (Exception e){ return null;}

        System.out.print("Number of Upper Berth(0/15) : ");
        try {
            uprb = Integer.parseInt(sc.nextLine());
        }catch (Exception e){ return null;}
        SeatModel[] seet;
        if(sitting==0 && lwrb==15 && (uprb==15||uprb==0) ||
            sitting==20 && lwrb==5 && (uprb==15||uprb==0) ||
            sitting==42 && lwrb==0 && (uprb==15||uprb==0)) {

            seet = new SeatModel[sitting + lwrb + uprb];
        }
        else {
            System.out.println("Sitting\tLower\tUpper");
            System.out.println("  20   \t   5 \t 15/0");
            System.out.println("   0   \t  15 \t 15/0");
            System.out.println("  42   \t   0 \t 15/0");
            return null;
        }

        b.setSeater(sitting);
        b.setLwrs(lwrb);
        b.setUprs(uprb);
        b.setCapacity(seet.length);

        for(int i=0;i< seet.length;i++) {
            seet[i] = new SeatModel();
            seet[i].setSeatNumber(i+1);
        }
        b.setSeat(seet);

        Date date_depart = new Date();
        Date date_arrive = new Date();

        System.out.print("Bus number(Only Number) : ");
        try {
            int number = Integer.parseInt(sc.nextLine());
            for(BusModel bus : DataSet.getObject().getBuses()) {
                if(bus.getNumber()==number) {
                    System.out.println("Bus Number already found...");
                    return null;
                }
            }
            b.setNumber(number);
        }catch (Exception e){ return null;}

        //b.setSeat(seet);

        System.out.print("Depart : ");
        String depart = sc.nextLine().toLowerCase();
        b.setDepart(depart);

        System.out.print("Depart Time (HH MM): ");
        try {
            int dhr = sc.nextInt();
            int dmm = sc.nextInt();sc.nextLine();
            date_depart.setHours(dhr);
            date_depart.setMinutes(dmm);
            b.setDepartTime(date_depart);
        }catch (Exception e){ return null;}


        System.out.print("Arrive : ");
        String arrive = sc.nextLine().toLowerCase();
        b.setArrive(arrive);

        System.out.print("Arrive Time(Hr MM) : ");
        try {
            int ahr = sc.nextInt();
            int amm = sc.nextInt();sc.nextLine();
            date_arrive.setHours(ahr);
            date_arrive.setMinutes(amm);
            b.setArriveTime(date_arrive);
        }catch (Exception e){ return null;}



        System.out.println("Boarding points : ");
        System.out.println("---------------------");
        Map<Integer,String> board = new TreeMap<>();
        Map<String,Date> boardTime = new TreeMap<>();
        board.put(0,depart);
        boardTime.put(depart,date_depart);

        int num = 0;
        while(true) {
            num++;

            System.out.println("Add\t0 for exit");
            System.out.println("---------------------");
            System.out.print("Place : ");
            String val = sc.nextLine();

            if(val.equals("0")) {
                //num=0;
                break;
            }
            Date d = new Date();
            System.out.println("---------------------");
            System.out.print("Time(Hr MM) : ");

            try {
                int hr = sc.nextInt();
                int min = sc.nextInt();sc.nextLine();
                d.setHours(hr);d.setMinutes(min);

                board.put(num, val);
                boardTime.put(val,d);
            }catch (Exception e){ return null;}

        }

        b.setBoardings(board);
        b.setBoardingTime(boardTime);

        return b;
    }

}
