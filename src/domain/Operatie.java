package domain;

import java.sql.*;

public class Operatie {
    protected long id;
    private String descriere;
    private float durata;
    private int cost;

    public Operatie(String descriere, float durata, int cost) throws Exception{
        this.id = -1;
        this.descriere = descriere;
        this.cost = cost;
        this.durata = durata;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescriere() {
        return descriere;
    }

    public float getDurata() {
        return durata;
    }

    public int getCost() {
        return cost;
    }
}
