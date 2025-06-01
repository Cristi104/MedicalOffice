package persistence;

import domain.Specializare;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class SpecializareRepository implements GenericRepository<Specializare> {
    private static String insertSQL = "INSERT INTO specializari(nume, descriere, salariu) VALUES(?, ?, ?)";
    private Connection connection;

    private static volatile SpecializareRepository instance;

    private SpecializareRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static SpecializareRepository getInstance() {
        if(instance == null){
            instance = new SpecializareRepository();
        }
        return instance;
    }

    @Override
    public Specializare save(Specializare entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getNume());
            stmt.setString(2, entity.getDescriere());
            stmt.setInt(3, entity.getSalariu());

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
    public List<Specializare> findAll() {
        return List.of();
    }

    @Override
    public Optional<Specializare> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Specializare entity) {

    }

    @Override
    public void delete(Specializare entity) {

    }
}
