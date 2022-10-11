package com.busbooking;

import com.busbooking.data.DataSet;
import com.busbooking.controller.*;
import com.busbooking.view.SearchingView;
import java.util.Scanner;

public class BusBookingMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DataSet.getObject();
        String username = "admin";
        String password = "123";
        int choice;
        do {
            choice = SearchingView.menu();

            switch (choice) {
                case 0:
                    SearchingView.thanksCard();
                    break;
                case 1:
                    System.out.println("----------------------------");
                    System.out.print("Username : ");
                    String u_name = sc.nextLine();
                    System.out.print("Password : ");
                    String pass = sc.nextLine();


                    if(u_name.equals(username) && pass.equals(password)) {
                        System.out.println("Welcome " + u_name);
                        Outer:
                        while(true) {
                            int ch = SearchingView.menu(u_name);
                            switch (ch) {
                                case 0:
                                    break Outer;
                                case 1:
                                    //Add bus
                                    new BusController().addBus();
                                    break;
                                case 2:
                                    //list of buses
                                    SearchingView.getObject().listOfBuses();
                                    break;
                                case 3:
                                    //date update(add/remove)
                                    Update.update();
                                    break;
                                default:
                                    System.err.println("---------Wrong input---------");
                                    Sleep.sleep();
                                    break;
                            }
                        }
                    }

                    else
                        System.err.println("---------Wrong username/password---------");

                    Sleep.sleep();

                    break;

                case 2:
                    //bus list base on root
                    try {
                        SearchingView.getObject().show();
                    }
                    catch (Exception e) {
                        System.err.println(e.getMessage());
                        Sleep.sleep();
                    }

                    break;
                case 3:
                    //book
                    BusController.book();
                    break;
                case 4:
                    //View
                    Update.view();
                    break;
                case 5:
                    //Delete
                    Update.delete();
                    break;
                default:
                    System.err.println("---------Wrong input---------");
                    Sleep.sleep();
                    break;

            }
        }while(choice != 0);
    }

}
