package domain;

import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Adresa {
    protected long id;
    private String oras;
    private String strada;
    private int numar;

    public Adresa(String oras, String strada, int numar) throws Exception{
        this.id = -1;
        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
