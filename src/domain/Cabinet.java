package domain;

import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Cabinet {
    protected long id;
    private String nume;
    private int etaj;
    private int numar;

    public Cabinet(String nume, int etaj, int numar) throws Exception{
        this.id = -1;
        this.nume = nume;
        this.etaj = etaj;
        this.numar = numar;
    }

    public Cabinet(Scanner in, PrintStream out) throws Exception{
        System.out.println("Nume cabinet:");
        String nume = in.nextLine();
        System.out.println("etaj:");
        int etaj = in.nextInt();
        System.out.println("numar:");
        int numar = in.nextInt();
        in.nextLine();

        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        this.id = -1;
        this.nume = nume;
        this.etaj = etaj;
        this.numar = numar;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public int getEtaj() {
        return etaj;
    }

    public int getNumar() {
        return numar;
    }
}
