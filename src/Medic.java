public class Medic extends Persoana{
    private Cabinet cabinet;
    private String specializare;

    public Medic(String nume, String prenume, String telefon, Cabinet cabinet, String specializare) throws Exception {
        super(nume, prenume, telefon);

        this.cabinet = cabinet;
        this.specializare = specializare;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public String getSpecializare() {
        return specializare;
    }
}
