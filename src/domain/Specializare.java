package domain;

import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Specializare {
    protected long id;
    private String nume;
    private String descriere;
    private float salariu;

    public Specializare(String nume, String descriere, float salariu) {
        this.nume = nume;
        this.descriere = descriere;
        this.salariu = salariu;
        this.id = -1;
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

    public String getDescriere() {
        return descriere;
    }

    public float getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return "nume: " + nume + " descriere: " + descriere + " salariu: " + salariu;
    }
}
