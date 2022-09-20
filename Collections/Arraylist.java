package com.collections;

import java.util.*;


class Employee {

    private String name;
    private int salary;
    private int id;

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    Employee(int id,String name,int salary) {
        this.id = id;
        this.name = name;   
        this.salary = salary;
    }

}

public class Arraylist {

    public static void main(String[] args) {

        List<Employee> employeeList = new ArrayList<Employee>();
        Employee emp1 = new Employee(1000,"Arun",50000);
        employeeList.add(emp1);

        ArrayList<Employee> emplst = new ArrayList<>();
        Employee emp2 = new Employee(1001,"Ajith",50000);
        Employee emp3 = new Employee(1002,"Sheak",50000);

        emplst.add(emp2);
        emplst.add(emp3);



        employeeList.addAll(emplst);


        for(Employee e:employeeList) {
            System.out.println(e.getId()+" "+e.getName());
        }

        emplst.clear(); //delete all records
        for(Employee e:emplst) {
            System.out.println(e.getId()+" "+e.getName());
        }

        System.out.println(employeeList.get(2).getName());

        Employee delete = employeeList.remove(1);
        employeeList.set(1,new Employee(1002,"Shek",50000));
        //System.out.println(employeeList.toString());
        Iterator itr = employeeList.iterator();

        while(itr.hasNext()){
            Employee emp = (Employee) itr.next();
            System.out.println(emp.getId()+" "+emp.getName());
        }




    }

}
