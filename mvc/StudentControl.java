package com.mvc;

public class StudentControl {

        private StudentModel student_model;
        private StudentView student_view;

        StudentControl(StudentModel s_model,StudentView s_view) {

            this.student_model = s_model;
            this.student_view = s_view;

        }

        /* model
            private int id;
            private String name;
            private double percentage;
         */
        public void setStudentName(String name) {
            this.student_model.setName(name);
        }

        public void setStudentId(int id) {
            this.student_model.setId(id);
        }

        public void setStudentPercentage(double percentage) {
            this.student_model.setPercentage(percentage);
        }

        public String getStudentName() {
            return this.student_model.getName();
        }
        public int getStudentId() {
            return this.student_model.getId();
        }
        public double getStudentPercentage() {
            return this.student_model.getPercentage();
        }

        public void display() {
            this.student_view.printDetails(this.student_model.getId(),this.student_model.getName(),this.student_model.getPercentage());
        }
}
