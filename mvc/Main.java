package com.mvc;

public class Main {

    public static void main(String[] args) {


        StudentView s_view = new StudentView();
        StudentModel s_model = s_view.getDetails();

        StudentControl student_control = new StudentControl(s_model,s_view);
        student_control.display();
        student_control.setStudentName("ARUNABISHEK");
        student_control.display();

    }

}
