package persistence;

import domain.Operatie;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class OperatieRepository implements GenericRepository<Operatie>{
    private static String insertSQL = "INSERT INTO operatii(descriere, durata, cost) VALUES(?, ?, ?)";
    private static String selectByIdSQL = "SELECT * FROM operatii WHERE id_operatie = ?";
    private Connection connection;

    private static volatile OperatieRepository instance;

    private OperatieRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static OperatieRepository getInstance() {
        if(instance == null){
            instance = new OperatieRepository();
        }
        return instance;
    }

    @Override
    public Operatie save(Operatie entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getDescriere());
            stmt.setFloat(2, entity.getDurata());
            stmt.setInt(3, entity.getCost());

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
    public List<Operatie> findAll() {
        return List.of();
    }

    @Override
    public Optional<Operatie> findById(String id) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByIdSQL);
            stmt.setLong(1, Long.parseLong(id));
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                String descriere = result.getString(2);
                float durata = result.getFloat(3);
                int cost = result.getInt(4);
                Operatie operatie = new Operatie(descriere, durata, cost);
                operatie.setId(Long.parseLong(id));
                return Optional.of(operatie);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Operatie entity) {

    }

    @Override
    public void delete(Operatie entity) {

    }
}
