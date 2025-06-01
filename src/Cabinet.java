import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;

public class Cabinet {
    protected long id;
    private String nume;
    private int etaj;
    private int numar;

    public Cabinet(String nume, int etaj, int numar) throws Exception{
        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        this.id = -1;
        this.nume = nume;
        this.etaj = etaj;
        this.numar = numar;
    }

    public Cabinet(Scanner in, PrintStream out) throws Exception{
        System.out.println("Nume cabinet:");
        String nume = in.nextLine();
        System.out.println("etaj:");
        int etaj = in.nextInt();
        System.out.println("numar:");
        int numar = in.nextInt();
        in.nextLine();

        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        this.id = -1;
        this.nume = nume;
        this.etaj = etaj;
        this.numar = numar;

    }

    public long insertIntoDatabase(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        String insertSQL = "INSERT INTO cabinete(nume, etaj, numar) VALUES(?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getNume());
            stmt.setInt(2, this.getEtaj());
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
