package persistence;

import domain.Cabinet;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CabinetRepository implements GenericRepository<Cabinet> {
    private static String insertSQL = "INSERT INTO cabinete(nume, etaj, numar) VALUES(?, ?, ?)";
    private Connection connection;

    private static volatile CabinetRepository instance;

    private CabinetRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static CabinetRepository getInstance() {
        if(instance == null){
            instance = new CabinetRepository();
        }
        return instance;
    }

    @Override
    public Cabinet save(Cabinet entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getNume());
            stmt.setInt(2, entity.getEtaj());
            stmt.setInt(3, entity.getNumar());

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
    public List<Cabinet> findAll() {
        return List.of();
    }

    @Override
    public Optional<Cabinet> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Cabinet entity) {

    }

    @Override
    public void delete(Cabinet entity) {

    }
}
