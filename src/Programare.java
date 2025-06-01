import java.sql.*;
import java.time.LocalDateTime;

public class Programare {
    protected long id;
    private Client client;
    private Medic medic;
    private LocalDateTime dateTime;
    private Operatie operatie;

    public Programare(Client client, Medic medic, LocalDateTime dateTime, Operatie serviciu) throws Exception{
        if(dateTime.isBefore(LocalDateTime.now())){
            throw new Exception("invalid date appointment cant be created in the past");
        }

        this.id = -1;
        this.client = client;
        this.medic = medic;
        this.dateTime = dateTime;
        this.operatie = serviciu;
    }

    public long insertIntoDatabase(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try{
            if(this.operatie.getId() == -1)
                this.operatie.insertIntoDatabase();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        String insertSQL = "INSERT INTO programari(id_client, id_medic, data, id_operatie) VALUES(?, ?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, this.client.getId());
            stmt.setLong(2,  this.medic.getId());
            stmt.setString(3, this.getDateTime().toLocalDate().toString() + " " + this.getDateTime().toLocalTime().toString());
            stmt.setLong(4,  this.operatie.getId());

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

    public Client getClient() {
        return client;
    }

    public Medic getMedic() {
        return medic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Operatie getOperatie() {
        return operatie;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
