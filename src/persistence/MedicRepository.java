package persistence;

import domain.Cabinet;
import domain.Medic;
import domain.Orar;
import domain.Specializare;
import persistence.util.DataBaseConnection;
import service.CabinetService;
import service.OrarService;
import service.SpecializareService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicRepository implements GenericRepository<Medic> {
    private static String insertSQL = "INSERT INTO medici(nume, prenume, telefon, id_cabinet, id_orar, id_specializare) VALUES(?, ?, ?, ?, ?, ?)";
    private static String selectAllSQL = "SELECT * FROM medici";
    private static String selectAllDateSQL = "SELECT * FROM medici m WHERE NOT EXISTS((SELECT * FROM programari p LEFT JOIN operatii o ON p.id_operatie = o.id_operatie WHERE p.id_medic = m.id_medic AND ? BETWEEN p.data AND DATE_ADD(p.data, INTERVAL o.durata HOUR)))";
    private static String selectByIdSQL = "SELECT * FROM medici WHERE id_medic = ?";
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
        List<Medic> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectAllSQL);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String telefon = result.getString(4);
                long id_cabinet = result.getLong(5);
                Cabinet cabinet = new CabinetService().getCabinetById(id_cabinet);
                long id_orar = result.getLong(6);
                Orar orar = new OrarService().getOrarById(id_orar);
                long id_specializare = result.getLong(7);
                Specializare specializare = new SpecializareService().getSpecializareById(id_specializare);
                Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, orar);
                medic.setId(id);
                list.add(medic);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Medic> findAll(LocalDateTime dateTime) {
        List<Medic> list = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(selectAllDateSQL);
            stmt.setString(1, dateTime.toLocalDate().toString() + " " + dateTime.toLocalTime().toString());
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                long id = result.getLong(1);
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String telefon = result.getString(4);
                long id_cabinet = result.getLong(5);
                Cabinet cabinet = new CabinetService().getCabinetById(id_cabinet);
                long id_orar = result.getLong(6);
                Orar orar = new OrarService().getOrarById(id_orar);
                long id_specializare = result.getLong(7);
                Specializare specializare = new SpecializareService().getSpecializareById(id_specializare);
                Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, orar);
                medic.setId(id);
                list.add(medic);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Medic> findById(String id) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByIdSQL);
            stmt.setLong(1, Long.parseLong(id));
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                String nume = result.getString(2);
                String prenume = result.getString(3);
                String telefon = result.getString(4);
                long id_cabinet = result.getLong(5);
                Cabinet cabinet = new CabinetService().getCabinetById(id_cabinet);
                long id_orar = result.getLong(6);
                Orar orar = new OrarService().getOrarById(id_orar);
                long id_specializare = result.getLong(7);
                Specializare specializare = new SpecializareService().getSpecializareById(id_specializare);
                Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, orar);
                medic.setId(Long.parseLong(id));
                return Optional.of(medic);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Medic entity) {

    }

    @Override
    public void delete(Medic entity) {

    }
}
