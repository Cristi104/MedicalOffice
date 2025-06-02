package domain;

import java.io.PrintStream;
import java.util.Scanner;

public class Client extends Persoana {
    private String CNP;
    private Adresa adresa;

    public Client(String nume, String prenume, String telefon, String CNP, Adresa adresa) throws Exception {
        super(nume, prenume, telefon);
        this.CNP = CNP;
        this.adresa = adresa;
    }

    public String getCNP() {
        return CNP;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public String toString(){
        return super.toString() + " CNP: " + CNP + " adresa: " + adresa.toString();
    }
}
