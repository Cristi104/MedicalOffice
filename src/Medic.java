import java.io.PrintStream;
import java.util.Scanner;

public class Medic extends Persoana{
    private Cabinet cabinet;
    private Specializare specializare;
    private Orar orar;

    public Medic(String nume, String prenume, String telefon, Cabinet cabinet, Specializare specializare, Orar orar) throws Exception {
        super(nume, prenume, telefon);

        this.cabinet = cabinet;
        this.specializare = specializare;
        this.orar = orar;
    }

    public Medic(Scanner in, PrintStream out) throws Exception{
        super(in, out);

        this.cabinet = new Cabinet(in, out);
        this.specializare = new Specializare(in, out);
        this.orar = new Orar();
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public Specializare getSpecializare() {
        return specializare;
    }

    public Orar getOrar() {
        return orar;
    }

    public String toString(){
        return super.toString() + " specializare: " + this.specializare;
    }
}
