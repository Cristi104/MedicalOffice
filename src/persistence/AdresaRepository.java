package persistence;

import domain.Adresa;
import domain.Specializare;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AdresaRepository implements GenericRepository<Adresa>{
    private static String insertSQL = "INSERT INTO adrese(oras, strada, numar) VALUES(?, ?, ?)";
    private static String selectByIdSQL = "SELECT * FROM adrese WHERE id_adresa = ?";
    private Connection connection;

    private static volatile AdresaRepository instance;

    private AdresaRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static AdresaRepository getInstance() {
        if(instance == null){
            instance = new AdresaRepository();
        }
        return instance;
    }

    @Override
    public Adresa save(Adresa entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getOras());
            stmt.setString(2, entity.getStrada());
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
    public List<Adresa> findAll() {
        return List.of();
    }

    @Override
    public Optional<Adresa> findById(String id) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByIdSQL);
            stmt.setLong(1, Long.parseLong(id));
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                String oras = result.getString(2);
                String strada = result.getString(3);
                int numar = result.getInt(4);
                Adresa adresa = new Adresa(oras, strada, numar);
                adresa.setId(Long.parseLong(id));
                return Optional.of(adresa);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Adresa entity) {

    }

    @Override
    public void delete(Adresa entity) {

    }
}
