public class Client extends Persoana{
    private String CNP;
    private Adresa adresa;

    public Client(String nume, String prenume, String telefon, String CNP, Adresa adresa) throws Exception {
        super(nume, prenume, telefon);

        if(CNP.length() != 13){
            throw new Exception("CNP invalid");
        }

        for(int i = 0; i < CNP.length(); i++){
            if(!Character.isDigit(CNP.charAt(i))){
                throw new Exception("CNP invalid");
            }
        }

        this.CNP = CNP;
        this.adresa = adresa;
    }

    public String getCNP() {
        return CNP;
    }

    public Adresa getAdresa() {
        return adresa;
    }
}
