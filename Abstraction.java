abstract class AnnaUniversity {

    void university() {
        System.out.println("Anna University");
    }

    abstract void civil();
    abstract void cse();
    abstract void ece();
    abstract void eee();
    abstract void mech();
}


class Jct extends AnnaUniversity {

    static int availableCivil = 90;
    static int availableCse = 120;
    static int availableEce = 120;
    static int availableEee = 90;
    static int availableMech = 90;
    static int availablefootTech = 180;
    static int availablepetrolium = 180;
    static int availablepetroChemical = 180;


    static {
        System.out.println("Welcome to Jct college");
    }
    @Override
    void civil() {

        System.out.println("Available Seets civil : " +availableCivil);

    }

    @Override
    void cse() {

        System.out.println("Available Seets cse : " +availableCse);
    }

    @Override
    void ece() {

        System.out.println("Available Seets ece : " +availableEce);
    }

    @Override
    void eee() {

        System.out.println("Available Seets eee : " +availableEee);
    }

    @Override
    void mech() {

        System.out.println("Available Seets mech : " +availableMech);
    }

    void footTech() {
        System.out.println("Available Seets footTech : " +availablefootTech);
    }

    void petrolium() {
        System.out.println("Available Seets petrolium : " +availablepetrolium);
    }

    void petroChemical() {
        System.out.println("Available Seets petroChemical : " +availablepetroChemical);
    }
}

class Mepco extends AnnaUniversity {

    static int availableCivil = 120;
    static int availableCse = 120;
    static int availableEce = 120;
    static int availableEee = 120;
    static int availableMech = 120;
    static int availableAids = 180;

    static {
        System.out.println("Welcome to Mepco college");
    }

    @Override
    void civil() {

        System.out.println("Available Seets civil : " +availableCivil);

    }

    @Override
    void cse() {

        System.out.println("Available Seets cse : " +availableCse);
    }

    @Override
    void ece() {

        System.out.println("Available Seets ece : " +availableEce);
    }

    @Override
    void eee() {

        System.out.println("Available Seets eee : " +availableEee);
    }

    @Override
    void mech() {

        System.out.println("Available Seets mech : " +availableMech);
    }

    void aids() {
        System.out.println("Available Seets aids : " +availableAids);
    }

}
public class Abstraction {

    public static void main(String[] args) {


        //depend on anna university
        AnnaUniversity college1 = new Jct();

        //Independent
        Jct college7209 = new Jct();

        college1.mech();
        college7209.footTech();

    }

}
