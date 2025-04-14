public class Adresa {
    private String oras;
    private String strada;
    private int numar;

    public Adresa(String oras, String strada, int numar) throws Exception{
        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
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
}
