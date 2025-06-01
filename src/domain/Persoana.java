package domain;

import java.io.PrintStream;
import java.util.Scanner;

public class Persoana {
    protected long id;
    protected String nume;
    protected String prenume;
    protected String telefon;

    public Persoana(String nume, String prenume, String telefon) throws Exception{
        this.nume = nume;
        this.prenume = prenume;
        this.id = -1;
        if(telefon.length() != 10){
            throw new Exception("numar de telefon invalid");
        }

        for(int i = 0; i < telefon.length(); i++){
            if(!Character.isDigit(telefon.charAt(i))){
                throw new Exception("numar de telefon invalid");
            }
        }

        this.telefon = telefon;
    }

    public Persoana(Scanner in, PrintStream out) throws Exception{
        System.out.println("Nume:");
        String nume = in.nextLine();
        System.out.println("Prenume:");
        String prenume = in.nextLine();
        System.out.println("Nr Telefon:");
        String telefon = in.nextLine();

        this.nume = nume;
        this.prenume = prenume;
        if(telefon.length() != 10){
            throw new Exception("numar de telefon invalid");
        }

        for(int i = 0; i < telefon.length(); i++){
            if(!Character.isDigit(telefon.charAt(i))){
                throw new Exception("numar de telefon invalid");
            }
        }

        this.id = -1;
        this.telefon = telefon;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return "Nume: " + this.nume + " prenume: " + this.prenume + " telefon: " + this.telefon;
    }
}
