package persistence;

import domain.Medic;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MedicRepository implements GenericRepository<Medic> {
    private static String insertSQL = "INSERT INTO medici(nume, prenume, telefon, id_cabinet, id_orar, id_specializare) VALUES(?, ?, ?, ?, ?, ?)";
    private Connection connection;

    private static volatile MedicRepository instance;

    private MedicRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static MedicRepository getInstance() {
        if(instance == null){
            instance = new MedicRepository();
        }
        return instance;
    }

    @Override
    public Medic save(Medic entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getNume());
            stmt.setString(2, entity.getPrenume());
            stmt.setString(3, entity.getTelefon());
            stmt.setLong(4, entity.getCabinet().getId());
            stmt.setLong(5, entity.getOrar().getId());
            stmt.setLong(6, entity.getSpecializare().getId());

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
    public List<Medic> findAll() {
        return List.of();
    }

    @Override
    public Optional<Medic> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Medic entity) {

    }

    @Override
    public void delete(Medic entity) {

    }
}
