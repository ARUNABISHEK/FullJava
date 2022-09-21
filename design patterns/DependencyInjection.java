package com.database;

class Address {

    int number;
    String street;
    String city;
    String district;
    int pincode;


    Address(int number,String street,String city,String district,int pincode) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String toString() {
        return "\n" + this.number+", "+this.street+",\n"+this.city+",\n"+this.district+" - "+this.pincode;
    }
}

class Employee {
    private int id;
    private String name;
    private int salary;

    private Address address;

    Employee(int id,String name,int salary,Address address) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }
}
public class DependencyInjection {

    public static void main(String[] args) {

        Address gopiaddress = new Address(7,"Main street","Ooty","Nilagiris",634125);
        Employee gopi = new Employee(1000,"Gopi",50000,gopiaddress);

        gopiaddress.setStreet("Ganthi nagar 1st street");

        System.out.println(gopi.getId() + " " + gopi.getName() + " " + gopi.getAddress().toString());

    }

}
