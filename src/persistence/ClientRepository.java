package persistence;

import domain.*;
import persistence.util.DataBaseConnection;
import service.AdresaService;
import service.CabinetService;
import service.OrarService;
import service.SpecializareService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements GenericRepository<Client> {
    private static String insertSQL = "INSERT INTO clienti(nume, prenume, CNP, telefon, id_adresa) VALUES(?, ?, ?, ?, ?)";
    private static String selectAllSQL = "SELECT * FROM clienti";
    private static String selectByIdSQL = "SELECT * FROM clienti WHERE id_client = ?";
    private static String selectByCNPSQL = "SELECT * FROM clienti WHERE CNP = ?";
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
        List<Client> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectAllSQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String CNP = result.getString(4);
                String telefon = result.getString(5);
                long id_adresa = result.getLong(6);
                Adresa adresa = new AdresaService().getAdresaById(id_adresa);
                Client client = new Client(nume, prenume, telefon, CNP, adresa);
                client.setId(id);
                list.add(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Client> findById(String id) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByIdSQL);
            stmt.setLong(1, Long.parseLong(id));
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String CNP = result.getString(4);
                String telefon = result.getString(5);
                long id_adresa = result.getLong(6);
                Adresa adresa = new AdresaService().getAdresaById(id_adresa);
                Client client = new Client(nume, prenume, telefon, CNP, adresa);
                client.setId(Long.parseLong(id));
                return Optional.of(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Client> findByCNP(String CNP) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByCNPSQL);
            stmt.setString(1, CNP);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String telefon = result.getString(5);
                long id_adresa = result.getLong(6);
                Adresa adresa = new AdresaService().getAdresaById(id_adresa);
                Client client = new Client(nume, prenume, telefon, CNP, adresa);
                client.setId(id);
                return Optional.of(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Client entity) {

    }

    @Override
    public void delete(Client entity) {

    }
}
