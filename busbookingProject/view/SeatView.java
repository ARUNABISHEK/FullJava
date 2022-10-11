package com.busbooking.view;


import com.busbooking.controller.Sleep;
import com.busbooking.data.DataSet;
import com.busbooking.model.BusModel;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class SeatView {

    public static boolean show(int num, Date d) {
        boolean bool = false;
        for (Map.Entry<String, List<BusModel>> bus_date : DataSet.getObject().getBus_date().entrySet()) {
            if (bus_date.getKey().equals(d.toString())) {
                for (BusModel bus : DataSet.getObject().getBus_date().get(d.toString())) {

                    if (bus.getNumber() == num) {
                        bool = true;
                        int seeter = bus.getSeater();
                        int lb = bus.getLwrs();
                        int upr = bus.getUprs();

                        int count = 0;


                        System.out.println("Lower : ");
                        //lower
                        if (seeter == 20) {

                            for (int i = 0; i < Math.floor(seeter / 2); i++) {
                                for (int j = 0; j < 4; j++) {
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (j == 1)
                                        System.out.print("|\t");
                                    else if (i % 2 != 0 && j == 0)
                                        System.out.print("\t");
                                    else {
                                        if (j == 0) {

                                            if (bus.getSeat()[count].isAvailable())
                                                System.out.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());
                                            else
                                                System.err.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());
                                        } else {
                                            if (bus.getSeat()[count].isAvailable()) {
                                                //check right
                                                if (j == 2 && bus.getSeat()[count + 1].getGenderOccupancy() == 'F') {
                                                    System.out.printf("%2dF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                                }
                                                //check left
                                                else if (j == 3 && bus.getSeat()[count - 1].getGenderOccupancy() == 'F') {
                                                    System.out.printf("%2dF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                                } else
                                                    System.out.printf("%2d\t", bus.getSeat()[++count - 1].getSeatNumber());

                                            } else
                                                System.err.printf("%2d\t", bus.getSeat()[++count - 1].getSeatNumber());

                                        }
                                    }
                                    Sleep.sleep();
                                }
                                System.out.println();
                            }
                        } else if (seeter == 0) {

                            for (int i = 0; i < Math.floor(lb / 3); i++) {

                                for (int j = 0; j < 4; j++) {
                                    if (j == 1)
                                        System.out.print("|\t");

                                    else {
                                        if (bus.getSeat()[count].isAvailable()) {
                                            //chech right
                                            if (j == 2 && bus.getSeat()[count + 1].getGenderOccupancy() == 'F') {
                                                System.out.printf("%2dsF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                            }
                                            //check left
                                            else if (j == 3 && bus.getSeat()[count - 1].getGenderOccupancy() == 'F') {
                                                System.out.printf("%2dsF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                            } else
                                                System.out.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());

                                        } else
                                            System.err.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());
                                    }
                                    Sleep.sleep();
                                }
                                System.out.println();
                            }
                        } else if (lb == 0) {

                            for (int i = 0; i < Math.floor(seeter / 4); i++) {

                                for (int j = 0; j < 6; j++) {

                                    if ((j == 2 || j == 3) && i != 9)
                                        System.out.print("\t");
                                    else {
                                        if (bus.getSeat()[count].isAvailable()) {


                                            //check right
                                            if ((j != 9 && (j == 0 || j == 4)) && bus.getSeat()[count + 1].getGenderOccupancy() == 'F') {
                                                System.out.printf("%2dF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                            }
                                            //check left
                                            else if ((j != 9 && (j == 1 || j == 5)) && bus.getSeat()[count - 1].getGenderOccupancy() == 'F') {
                                                System.out.printf("%2dF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                            } else
                                                System.out.printf("%2d\t", bus.getSeat()[++count - 1].getSeatNumber());
                                        } else
                                            System.err.printf("%2d\t", bus.getSeat()[++count - 1].getSeatNumber());
                                    }
                                    Sleep.sleep();
                                }
                                System.out.println();
                            }
                        }

                        System.out.println("Upper : ");
                        //upper

                        for (int i = 0; i < Math.floor(upr / 3); i++) {
                            for (int j = 0; j < 4; j++) {

                                if (j == 1)
                                    System.out.print("|\t");
                                else {
                                    if (bus.getSeat()[count].isAvailable()) {
                                        //System.out.printf("%2ds\t", bus.getSeet()[++count - 1].getSeetNumber());
                                        if (j == 2 && bus.getSeat()[count + 1].getGenderOccupancy() == 'F') {
                                            System.out.printf("%2dsF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                        }
                                        //check left
                                        else if (j == 3 && bus.getSeat()[count - 1].getGenderOccupancy() == 'F') {
                                            System.out.printf("%2dsF\t", bus.getSeat()[++count - 1].getSeatNumber());
                                        } else
                                            System.out.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());
                                    } else
                                        System.err.printf("%2ds\t", bus.getSeat()[++count - 1].getSeatNumber());

                                }
                                Sleep.sleep();
                            }
                            System.out.println();
                        }

                    }
                }
            }
        }
        if(!bool) {
            System.err.println("Oops check travel date/");

        }
        return bool;
    }

}
