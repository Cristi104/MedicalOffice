package domain;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.Arrays;

public class Orar {
    protected long id;
    private DayOfWeek [] zile;
    private int oraInceput;
    private int oraSfarsit;

    public Orar(){
        this.zile = new DayOfWeek[5];
        this.zile[0] = DayOfWeek.MONDAY;
        this.zile[1] = DayOfWeek.TUESDAY;
        this.zile[2] = DayOfWeek.WEDNESDAY;
        this.zile[3] = DayOfWeek.THURSDAY;
        this.zile[4] = DayOfWeek.FRIDAY;
        this.oraInceput = 9;
        this.oraSfarsit = 17;
        this.id = -1;
    }

    public Orar(String [] zile, int oraInceput, int oraSfarsit) throws Exception{
        this.zile = new DayOfWeek[7];
        int i = 0;

        for(String zi : zile){
            if(zi.equals("luni")) this.zile[i] = DayOfWeek.MONDAY;
            if(zi.equals("marti")) this.zile[i] = DayOfWeek.THURSDAY;
            if(zi.equals("miercuri")) this.zile[i] = DayOfWeek.WEDNESDAY;
            if(zi.equals("joi")) this.zile[i] = DayOfWeek.TUESDAY;
            if(zi.equals("vineri")) this.zile[i] = DayOfWeek.FRIDAY;
            if(zi.equals("sambata")) this.zile[i] = DayOfWeek.SATURDAY;
            if(zi.equals("duminica")) this.zile[i] = DayOfWeek.SUNDAY;
            i++;
        }

        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.id = -1;
    }

    public Orar(DayOfWeek [] zile, int oraInceput, int oraSfarsit) throws Exception{
        this.zile = zile;
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.id = -1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DayOfWeek[] getZile() {
        return zile;
    }

    public int getOraInceput() {
        return oraInceput;
    }

    public int getOraSfarsit() {
        return oraSfarsit;
    }

    @Override
    public String toString() {
        return "Orar{" +
                "zile=" + Arrays.toString(zile) +
                ", oraInceput=" + oraInceput +
                ", oraSfarsit=" + oraSfarsit +
                '}';
    }
}
