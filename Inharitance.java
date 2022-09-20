class Parent {

    int area = 10;

    public void land() {
        System.out.println(area + "cent");
    }

}

class Child1 extends Parent{
    int ownArea = 25;
    int area = super.area/2 + ownArea;
    public void land() {
        System.out.println(area + "cent");
    }
}

class Child2 extends Parent{
    int ownArea = 10;
    int area = super.area/2 + ownArea;
    public void land() {
        System.out.println(area + "cent");
    }
}


class GrandChild extends Child1 {
    int ownArea = 10;
    int area = super.area + ownArea;
    public void land() {
        System.out.println(area + "cent");
    }
}

public class Inharitance {

    public static void main(String[] args) {

        Parent c1 = new Child1();
        c1.land();
        Parent c2 = new Child2();
        c2.land();

        Child1 gc = new GrandChild();
        gc.land();
    }
}