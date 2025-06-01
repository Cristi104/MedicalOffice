import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

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

    public Client(Scanner in, PrintStream out) throws Exception {
        super(in, out);

        System.out.println("CNP:");
        String CNP = in.nextLine();

        if(CNP.length() != 13){
            throw new Exception("CNP invalid");
        }

        for(int i = 0; i < CNP.length(); i++){
            if(!Character.isDigit(CNP.charAt(i))){
                throw new Exception("CNP invalid");
            }
        }

        this.CNP = CNP;
        this.adresa = new Adresa(in, out);
    }

    public long insertIntoDatabase(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try{
            if(this.adresa.getId() == -1)
                this.adresa.insertIntoDatabase();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        String insertSQL = "INSERT INTO clienti(nume, prenume, CNP, telefon, id_adresa) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getNume());
            stmt.setString(2, this.getPrenume());
            stmt.setString(3, this.getCNP());
            stmt.setString(4, this.getTelefon());
            stmt.setLong(5, this.adresa.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long insertedId = generatedKeys.getLong(1);
                    this.id = insertedId;
                    return insertedId;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return -1;
    }

    public String getCNP() {
        return CNP;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public String toString(){
        return super.toString() + " CNP: " + CNP + " adresa: " + adresa.toString();
    }
}
