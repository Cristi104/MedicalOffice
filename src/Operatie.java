import java.sql.*;

public class Operatie {
    protected long id;
    private String descriere;
    private float durata;
    private int cost;

    public Operatie(String descriere, float durata, int cost) throws Exception{
        if(durata <= 0 || durata >= 16){
            throw new Exception("durata invalida");
        }

        if(cost < 0){
            throw new Exception("cost invalid");
        }

        this.id = -1;
        this.descriere = descriere;
        this.cost = cost;
        this.durata = durata;
    }

    public long insertIntoDatabase(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        String insertSQL = "INSERT INTO operatii(descriere, durata, cost) VALUES(?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.getDescriere());
            stmt.setFloat(2, this.getDurata());
            stmt.setInt(3, this.getCost());

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

    public String getDescriere() {
        return descriere;
    }

    public float getDurata() {
        return durata;
    }

    public int getCost() {
        return cost;
    }
}
