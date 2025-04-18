import java.time.DayOfWeek;
import java.util.Arrays;

public class Orar {
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
    }

    public Orar(String [] zile, int oraInceput, int oraSfarsit) throws Exception{
        if (oraInceput < 0 || oraInceput > 23){
            throw new Exception("ora de inceput nu este o ora normala");
        }

        if (oraSfarsit < 0 || oraSfarsit > 23 || oraInceput >= oraSfarsit){
            throw new Exception("ora de sfarsit nu este o ora normala");
        }

        this.zile = new DayOfWeek[7];
        int i = 0;

        for(String zi : zile){
            if(!zi.equals("luni") && !zi.equals("marti") && !zi.equals("miercuri") && !zi.equals("joi") && !zi.equals("vineri") && !zi.equals("sambata") && !zi.equals("duminica")){
                throw new Exception(zi + " nu este o zi a saptamani (luni, marti, miercuri, joi, vineri, sambata, duminica)");
            }
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
}
