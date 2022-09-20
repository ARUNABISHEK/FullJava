class Present {
    static int presentyCount = 0;
    String id;

    Present() {
        presentyCount++;
        this.id = "Guest id";
    }
    Present(String id) {
        presentyCount++;
        this.id = id;
    }

}

class Join {
    static int joinyCount = 0;
    String id;
    Join() {
        joinyCount++;
        this.id = "Guest id";
    }
    //constructor overloading
    Join(String id) {
        joinyCount++;
        this.id = id;
    }
}
class Meeting {

    static int presentCount = 0;
    static int joinCount = 0;

    Present[] present = new Present[5];
    Join[] join = new Join[150];

    public void add(Present p) {
        present[presentCount++] = p;
    }

    //Method overloading
    public void add(Join j) {
        join[joinCount++] = j;
    }

    public void List() {
        System.out.println();
        System.out.println("Presenting...");
        for(int i=0;i<present.length;i++) {
            if(present[i]==null)
                break;
            System.out.println(present[i].id + " - " + (i+1));
        }
        System.out.println();
        System.out.println("Joinys...");
        for(int i=0;i<join.length;i++) {
            if(join[i]==null)
                break;
            System.out.println(join[i].id + " - " + (i+1));
        }


    }
}

public class Polymorphism {

    public static void main(String[] args) {

        Meeting meet = new Meeting();

        Present p1 = new Present("p@1.zohocorp.com");
        Present p2 = new Present("p@2.zohocorp.com");

        Join j1 = new Join("j@1.zohocorp.com");
        Join j2 = new Join("j@2.zohocorp.com");
        Join j3 = new Join("j@3.zohocorp.com");
        Join j4 = new Join("j@4.zohocorp.com");
        Join j5 = new Join("j@5.zohocorp.com");

        meet.add(p1);
        meet.add(p2);

        meet.add(j1);
        meet.add(j2);
        meet.add(j3);
        meet.add(j4);
        meet.add(j5);

        System.out.println("Total Joinys : " + Meeting.joinCount);
        System.out.println("Total Presentys : " + Meeting.presentCount);

        meet.List();
    }

}
