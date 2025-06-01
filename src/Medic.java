import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Medic extends Persoana{
    private Cabinet cabinet;
    private Specializare specializare;
    private Orar orar;

    public Medic(String nume, String prenume, String telefon, Cabinet cabinet, Specializare specializare, Orar orar) throws Exception {
        super(nume, prenume, telefon);

        this.cabinet = cabinet;
        this.specializare = specializare;
        this.orar = orar;
    }

    public Medic(Scanner in, PrintStream out) throws Exception{
        super(in, out);

        this.cabinet = new Cabinet(in, out);
        this.specializare = new Specializare(in, out);
        this.orar = new Orar();
    }

    public long insertIntoDatabse(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try{
            if(this.cabinet.getId() == -1)
                this.cabinet.insertIntoDatabase();
            if(this.orar.getId() == -1)
                this.orar.insertIntoDatabse();
            if(this.specializare.getId() == -1)
                this.specializare.insertIntoDatabse();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        String insertSQL = "INSERT INTO medici(nume, prenume, telefon, id_cabinet, id_orar, id_specializare) VALUES(?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getNume());
            stmt.setString(2, this.getPrenume());
            stmt.setString(3, this.getTelefon());
            stmt.setLong(4, this.cabinet.getId());
            stmt.setLong(5, this.orar.getId());
            stmt.setLong(6, this.specializare.getId());

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

    public Cabinet getCabinet() {
        return cabinet;
    }

    public Specializare getSpecializare() {
        return specializare;
    }

    public Orar getOrar() {
        return orar;
    }

    public String toString(){
        return super.toString() + " specializare: " + this.specializare;
    }
}
