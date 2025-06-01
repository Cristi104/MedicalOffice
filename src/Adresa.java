import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Adresa {
    protected long id;
    private String oras;
    private String strada;
    private int numar;

    public Adresa(String oras, String strada, int numar) throws Exception{
        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        this.id = -1;
        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public Adresa(Scanner in, PrintStream out) throws Exception{
        System.out.println("Oras:");
        String oras = in.nextLine();
        System.out.println("Strada:");
        String strada = in.nextLine();
        System.out.println("Nr:");
        int numar = in.nextInt();

        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        this.id = -1;
        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public long insertIntoDatabase(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        String insertSQL = "INSERT INTO adrese(oras, strada, numar) VALUES(?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getOras());
            stmt.setString(2, this.getStrada());
            stmt.setInt(3, this.getNumar());

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

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public int getNumar() {
        return numar;
    }

    public String toString(){
        return oras + " " + strada + " " + numar;
    }
}
