import java.io.PrintStream;
import java.util.Scanner;

public class Adresa {
    private String oras;
    private String strada;
    private int numar;

    public Adresa(String oras, String strada, int numar) throws Exception{
        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public Adresa(Scanner in, PrintStream out) throws Exception{
        System.out.println("Oras:");
        String oras = in.nextLine();
        System.out.println("Strada:");
        String strada = in.nextLine();
        System.out.println("Nr:");
        int numar = in.nextInt();

        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public int getNumar() {
        return numar;
    }

    public String toString(){
        return oras + " " + strada + " " + numar;
    }
}
