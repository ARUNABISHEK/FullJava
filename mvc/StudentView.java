package com.mvc;

import java.util.Scanner;

public class StudentView {

    Scanner sc = new Scanner(System.in);

    public StudentModel getDetails() {
        StudentModel student = new StudentModel();
        System.out.println("Enter id : ");
        int id = sc.nextInt();sc.nextLine();
        System.out.println("Name : ");
        String name = sc.nextLine();
        System.out.println("Percentage : ");
        Double percentage = sc.nextDouble();

        student.setId(id);
        student.setName(name);
        student.setPercentage(percentage);

        return student;
    }
    public void printDetails(int id,String name,double percentage) {
        System.out.println(id + " : " + name + " : " + percentage);
    }

}
