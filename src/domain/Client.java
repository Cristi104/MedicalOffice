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

    public Client(Scanner in, PrintStream out) throws Exception {
        super(in, out);

        System.out.println("CNP:");
        String CNP = in.nextLine();

        if(CNP.length() != 13){
            throw new Exception("CNP invalid");
        }

        for(int i = 0; i < CNP.length(); i++){
            if(!Character.isDigit(CNP.charAt(i))){
                throw new Exception("CNP invalid");
            }
        }

        this.CNP = CNP;
        this.adresa = new Adresa(in, out);
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
