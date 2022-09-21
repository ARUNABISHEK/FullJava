package com.database;

final class Accounts {
    private String acnumber;
    private String name;

    Accounts(String ac,String name) {
        this.acnumber = ac;
        this.name = name;
    }
    public String getAcnumber() {
        return acnumber;
    }

    public String getName() {
        return name;
    }
}

public class ImmutableClass {

    public static void main(String[] args) {

        Accounts arunAcc = new Accounts("1234567890","Arun");
        Accounts vinothAcc = new Accounts("9876543210","Vinoth");
        System.out.println(arunAcc.getName() + " " + arunAcc.getAcnumber());
        System.out.println(vinothAcc.getName() + " " +vinothAcc.getAcnumber());

        // arunAcc.name;    private
    }

}
