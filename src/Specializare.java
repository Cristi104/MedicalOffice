import java.io.PrintStream;
import java.util.Scanner;

public class Specializare {
    private String nume;
    private String descriere;
    private int salariu;

    public Specializare(String nume, String descriere, int salariu) {
        this.nume = nume;
        this.descriere = descriere;
        this.salariu = salariu;
    }

    public Specializare(Scanner in, PrintStream out) {
        System.out.println("Nume specializare:");
        String nume = in.nextLine();
        System.out.println("Descriere specializare:");
        String descriere = in.nextLine();
        System.out.println("salariu specializare:");
        int salariu = in.nextInt();

        this.nume = nume;
        this.descriere = descriere;
        this.salariu = salariu;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public int getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return "nume: " + nume + " descriere: " + descriere + " salariu: " + salariu;
    }
}
