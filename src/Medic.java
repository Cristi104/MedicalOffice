public class Medic extends Persoana{
    private Cabinet cabinet;
    private String specializare;
    private Orar orar;

    public Medic(String nume, String prenume, String telefon, Cabinet cabinet, String specializare, Orar orar) throws Exception {
        super(nume, prenume, telefon);

        this.cabinet = cabinet;
        this.specializare = specializare;
        this.orar = orar;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public String getSpecializare() {
        return specializare;
    }

    public Orar getOrar() {
        return orar;
    }

    public String toString(){
        return super.toString() + " specializare: " + this.specializare;
    }
}
