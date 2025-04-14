public class Cabinet {
    private String nume;
    private int etaj;
    private int numar;

    public Cabinet(String nume, int etaj, int numar) throws Exception{
        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        this.nume = nume;
        this.etaj = etaj;
        this.numar = numar;
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
