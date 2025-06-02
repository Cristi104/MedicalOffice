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
