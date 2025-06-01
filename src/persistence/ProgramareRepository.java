package persistence;

import domain.Programare;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProgramareRepository implements GenericRepository<Programare> {
    private static String insertSQL = "INSERT INTO programari(id_client, id_medic, data, id_operatie) VALUES(?, ?, ?, ?)";
    private Connection connection;

    private static volatile ProgramareRepository instance;

    private ProgramareRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static ProgramareRepository getInstance() {
        if(instance == null){
            instance = new ProgramareRepository();
        }
        return instance;
    }

    @Override
    public Programare save(Programare entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, entity.getClient().getId());
            stmt.setLong(2,  entity.getMedic().getId());
            stmt.setString(3, entity.getDateTime().toLocalDate().toString() + " " + entity.getDateTime().toLocalTime().toString());
            stmt.setLong(4,  entity.getOperatie().getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long insertedId = generatedKeys.getLong(1);
                    entity.setId(insertedId);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public List<Programare> findAll() {
        return List.of();
    }

    @Override
    public Optional<Programare> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Programare entity) {

    }

    @Override
    public void delete(Programare entity) {

    }
}
