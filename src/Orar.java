import java.util.Arrays;

public class Orar {
    private String [] zile;
    private int oraInceput;
    private int oraSfarsit;

    public Orar(String [] zile, int oraInceput, int oraSfarsit) throws Exception{
        for(String zi : zile){
            if(!zi.equals("luni") && !zi.equals("marti") && !zi.equals("miercuri") && !zi.equals("joi") && !zi.equals("vineri") && !zi.equals("sambata") && !zi.equals("duminica")){
                throw new Exception(zi + " nu este o zi a saptamani (luni, marti, miercuri, joi, vineri, sambata, duminica)");
            }
        }

        if (oraInceput < 0 || oraInceput > 23){
            throw new Exception("ora de inceput nu este o ora normala");
        }

        if (oraSfarsit < 0 || oraSfarsit > 23 || oraInceput >= oraSfarsit){
            throw new Exception("ora de sfarsit nu este o ora normala");
        }

        this.zile = Arrays.copyOf(zile, zile.length);
        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
    }

    public String[] getZile() {
        return zile;
    }

    public int getOraInceput() {
        return oraInceput;
    }

    public int getOraSfarsit() {
        return oraSfarsit;
    }
}
