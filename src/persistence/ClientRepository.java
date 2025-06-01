package persistence;

import domain.Client;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements GenericRepository<Client> {
    private static String insertSQL = "INSERT INTO clienti(nume, prenume, CNP, telefon, id_adresa) VALUES(?, ?, ?, ?, ?)";
    private Connection connection;

    private static volatile ClientRepository instance;

    private ClientRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static ClientRepository getInstance() {
        if(instance == null){
            instance = new ClientRepository();
        }
        return instance;
    }

    @Override
    public Client save(Client entity) {
        Connection connection = DataBaseConnection.getInstance().getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getNume());
            stmt.setString(2, entity.getPrenume());
            stmt.setString(3, entity.getCNP());
            stmt.setString(4, entity.getTelefon());
            stmt.setLong(5, entity.getAdresa().getId());

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
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public Optional<Client> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Client entity) {

    }

    @Override
    public void delete(Client entity) {

    }
}
