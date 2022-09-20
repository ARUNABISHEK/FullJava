import java.util.Scanner;


public class Exception {

    public static void main(String[] args) {

        int[] arr = new int[5];

        for(int i=0;i<10;i++) {
            try{
                arr[i] = i;
            }
            catch (ArrayIndexOutOfBoundsException ae) {
                ae.printStackTrace();
            }
        }

        try{
            int x = 5/0;
        } catch(ArithmeticException ae) {
            ae.printStackTrace();
        } finally {
            System.out.println("Always working fine");
        }

        System.out.println("work fine");

    }

}
