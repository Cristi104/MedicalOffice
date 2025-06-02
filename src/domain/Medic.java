package domain;

import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Medic extends Persoana {
    private Cabinet cabinet;
    private Specializare specializare;
    private Orar orar;

    public Medic(String nume, String prenume, String telefon, Cabinet cabinet, Specializare specializare, Orar orar) throws Exception {
        super(nume, prenume, telefon);

        this.cabinet = cabinet;
        this.specializare = specializare;
        this.orar = orar;
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
        return super.toString() + "orar: " + this.orar + " specializare: " + this.specializare;
    }
}
