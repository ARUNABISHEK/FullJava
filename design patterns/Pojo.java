package com.database;

class Data {
    private int id;
    private String name;
    private String dept;


    Data(int id,String name,String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String college) {
        this.dept = college;
    }
}
public class Pojo {
    //Plane Old Java Object
    public static void main(String[] args) {

        Data d1 = new Data(1,"Arun","CSE");
        Data d2 = new Data(2,"Vinoth","CSE");
        Data d3 = new Data(3,"Gopi","ECE");

        d3.setName("Gopinath");
        System.out.println(d1.getName()+"\n"+d2.getName()+"\n"+d3.getName());

    }
}