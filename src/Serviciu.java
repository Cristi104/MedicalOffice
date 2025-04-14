public class Serviciu {
    private String descriere;
    private float durata;
    private int cost;

    public Serviciu(String descriere, float durata, int cost) throws Exception{
        if(durata <= 0 || durata >= 16){
            throw new Exception("durata invalida");
        }

        if(cost < 0){
            throw new Exception("cost invalid");
        }

        this.descriere = descriere;
        this.cost = cost;
        this.durata = durata;
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
