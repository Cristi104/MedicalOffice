import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Specializare {
    protected long id;
    private String nume;
    private String descriere;
    private int salariu;

    public Specializare(String nume, String descriere, int salariu) {
        this.nume = nume;
        this.descriere = descriere;
        this.salariu = salariu;
        this.id = -1;
    }

    public Specializare(Scanner in, PrintStream out) {
        System.out.println("Nume specializare");
        String nume = in.nextLine();
        System.out.println("Descriere specializare");
        String descriere = in.nextLine();
        System.out.println("salariu specializare");
        int salariu = in.nextInt();

        this.id = -1;
        this.nume = nume;
        this.descriere = descriere;
        this.salariu = salariu;
    }

    public long insertIntoDatabse(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        String insertSQL = "INSERT INTO specializari(nume, descriere, salariu) VALUES(?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getNume());
            stmt.setString(2, this.getDescriere());
            stmt.setInt(3, this.getSalariu());

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

    public long getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public int getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return "nume: " + nume + " descriere: " + descriere + " salariu: " + salariu;
    }
}
