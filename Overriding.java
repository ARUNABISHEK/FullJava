import java.util.Scanner;

abstract class Employee1 {
    static boolean in = false;
    static boolean inOutSide = false;

    static int doorNo;

    abstract public void entry();
    abstract public void exit();
}

interface Maindoor{

    final int doorNo = 100;
    default void entry() {
        Employee1.in = true;
        Employee1.inOutSide = false;
        System.out.println("In office");
    }


    default void exit() {
        Employee1.in = false;
        Employee1.inOutSide = false;
        System.out.println("out");
    }
}

interface Lunchhall{

    final int doorNo = 101;
    default void entry() {
        Employee1.in = true;
        Employee1.inOutSide = true;
        System.out.println("in office Out Side");
    }


    default void exit() {
        Employee1.in = true;
        Employee1.inOutSide = true;
        System.out.println("in office out side");
    }
}

class Attendence extends Employee1 implements Maindoor,Lunchhall {

    @Override
    public void entry() {
        System.out.println("door no : ");
        Employee1.doorNo = Overriding.sc.nextInt();Overriding.sc.nextLine();

        if(Employee1.doorNo == Maindoor.doorNo) {
            Maindoor.super.entry();
        }
        else if(Employee1.doorNo == Lunchhall.doorNo) {
            Lunchhall.super.entry();
        }
    }

    @Override
    public void exit() {
        System.out.println("door no : ");
        Employee1.doorNo = Overriding.sc.nextInt();Overriding.sc.nextLine();

        if(Employee1.doorNo == Maindoor.doorNo) {
            Maindoor.super.exit();
        }
        else if(Employee1.doorNo == Lunchhall.doorNo) {
            Lunchhall.super.exit();
        }
    }
}
public class Overriding {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Employee1 emp1 = new Attendence();
        while(true) {
            System.out.println("1.entry\n2.exit\nOption : ");
            int ch = sc.nextInt();

            if(ch==1)
                emp1.entry();
            else if(ch==2)
                emp1.exit();

            if(!Employee1.in)
                break;
        }
    }

}
