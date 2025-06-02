package persistence;

import domain.Client;
import domain.Medic;
import domain.Operatie;
import domain.Programare;
import persistence.util.DataBaseConnection;
import service.ClientService;
import service.MedicService;
import service.OperatieService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramareRepository implements GenericRepository<Programare> {
    private static String insertSQL = "INSERT INTO programari(id_client, id_medic, data, id_operatie) VALUES(?, ?, ?, ?)";
    private static String selectAllSQL = "SELECT * FROM programari";
    private static String selectByDateAndMedicSQL = "SELECT * FROM programari WHERE DATE(data) = ? AND id_medic = ?";
    private static String selectByClientSQL = "SELECT * FROM programari WHERE id_client = ?";
    private static String deleteSQL = "DELETE FROM programari WHERE id_programare = ?";
    private static String updateSQL = "UPDATE programari SET id_client = ?, id_medic = ?, data = ?, id_operatie = ? WHERE id_programare = ?";
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Programare> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectAllSQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                long id_client = result.getLong(2);
                long id_medic = result.getLong(3);
                LocalDateTime dateTime = LocalDateTime.parse(result.getString(4), formatter);
                long id_operatie = result.getLong(5);
                Client client;
                client = new ClientService().getClientById(id_client);
                Medic medic;
                medic = new MedicService().getMedicById(id_medic);
                Operatie operatie;
                operatie = new OperatieService().getClientById(id_operatie);
                Programare programare = new Programare(client, medic, dateTime, operatie);
                programare.setId(id);
                list.add(programare);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Programare> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void update(Programare entity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            PreparedStatement stmt = connection.prepareStatement(updateSQL);
            stmt.setLong(1, entity.getClient().getId());
            stmt.setLong(2, entity.getMedic().getId());
            stmt.setString(3, entity.getDateTime().toLocalDate().toString() + " " + entity.getDateTime().toLocalTime().toString());
            stmt.setLong(4, entity.getOperatie().getId());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Programare> getProgramariMedic(Medic medic, LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Programare> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByDateAndMedicSQL);
            stmt.setDate(1, Date.valueOf(date));
            stmt.setLong(2, medic.getId());
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                long id_client = result.getLong(2);
                LocalDateTime dateTime = LocalDateTime.parse(result.getString(4), formatter);
                long id_operatie = result.getLong(5);
                Client client;
                client = new ClientService().getClientById(id_client);
                Operatie operatie;
                operatie = new OperatieService().getClientById(id_operatie);
                Programare programare = new Programare(client, medic, dateTime, operatie);
                programare.setId(id);
                list.add(programare);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Programare> getProgramariClient(Client client){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Programare> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByClientSQL);
            stmt.setLong(1, client.getId());
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                long id_medic = result.getLong(3);
                LocalDateTime dateTime = LocalDateTime.parse(result.getString(4), formatter);
                long id_operatie = result.getLong(5);
                Medic medic;
                medic = new MedicService().getMedicById(id_medic);
                Operatie operatie;
                operatie = new OperatieService().getClientById(id_operatie);
                Programare programare = new Programare(client, medic, dateTime, operatie);
                programare.setId(id);
                list.add(programare);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void delete(Programare entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(deleteSQL);
            stmt.setLong(1, entity.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
