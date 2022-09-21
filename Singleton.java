
class ZohoCorp {

    static ZohoCorp zoho;
    private ZohoCorp(){
        System.out.println("Welcome to Zoho Corporation");
    }

    public static ZohoCorp getBranch() {
        if(zoho==null)
            zoho = new ZohoCorp();
        return zoho;
    }

    public void branches(String str,String place) {
        System.out.println("Zoho corp - " + place + ", " + str);
    }
}

public class Singleton {

    public static void main(String[] args) {

        ZohoCorp chennai = ZohoCorp.getBranch();
        ZohoCorp tenkasi = ZohoCorp.getBranch();
        ZohoCorp covai = ZohoCorp.getBranch();

        if(chennai.hashCode() == tenkasi.hashCode() && chennai.hashCode()==covai.hashCode() && tenkasi.hashCode() == covai.hashCode()) {
                System.out.println("Single object only created...");
        }
        chennai.branches("Chennai", "Potheri");
        tenkasi.branches("Tenkasi", "Mathalamparai");
        covai.branches("Coimbatore", "Chiniyampalayam");
    }

}
