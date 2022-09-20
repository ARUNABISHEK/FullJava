
//Interface demo using meet application

import java.util.Scanner;

interface Access {
    public void microPhone();
    public void video();
    public void screenShare();
}

class Meet implements Access {
    Scanner sc = new Scanner(System.in);
    private boolean location = false;
    private boolean mic = false;
    private boolean video = false;



    private static boolean micswitch = false;
    private static boolean videoswitch = false;

    private static boolean screenswitch = false;
    public void setLocation() {
        location = true;
    }
    public void setMicrophone() {
        mic = true;
    }
    public void setVideo() {
        video = true;
    }

    public void screenShare(){


        if(!screenswitch) {
            screenswitch = true;
            System.out.println("Shreen shared...");
        }
        else {
            screenswitch = false;
            System.out.println("Screen sharing Off...");
        }

    }
    public void microPhone(){

        if(mic==true) {
            if(!micswitch) {
                micswitch = true;
                System.out.println("Microphone On...");
            }
            else {
                micswitch = false;
                System.out.println("Microphone Off...");
            }
        }

        else {
            System.out.println("Microphone and video Access 1/0");
            int micvdo = sc.nextInt();
            if(micvdo == 1) {
                setMicrophone();
                setVideo();
            }
        }
    }
    public void video() {
        if(video==true){
            if(!videoswitch) {
                videoswitch = true;
                System.out.println("Video On...");
            }
            else {
                videoswitch = false;
                System.out.println("video Off...");
            }
        }

        else {
            System.out.println("Microphone and video Access 1/0");
            int micvdo = sc.nextInt();
            if(micvdo == 1) {
                setMicrophone();
                setVideo();
            }
        }
    }
}
public class Interface
{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("You will allow location, microPhone and video...");

        Meet meet = new Meet();

        System.out.println("Location Access 1/0");
        int loc = sc.nextInt();
        if(loc == 1) {
            meet.setLocation();
        }

        System.out.println("Microphone and video Access 1/0");
        int micvdo = sc.nextInt();
        if(micvdo == 1) {
            meet.setMicrophone();
            meet.setVideo();
        }



        int choice = -1;
        do {
            System.out.println("1.microPhone Access \n2.video Access\n3.Screen Sharing\n4.Exit");
            choice = sc.nextInt();sc.nextLine();
            switch(choice) {
                case 1:
                    meet.microPhone();
                    break;
                case 2:
                    meet.video();
                    break;
                case 3:
                    meet.screenShare();
                    break;
                case 4:
                    System.out.println("Thankyou for using this application");
                    System.exit(0);
                default:
                    System.err.println("You entered wrong information");
            }
        }while(choice!=4);
    }
}
