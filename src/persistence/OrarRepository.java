package persistence;

import domain.Cabinet;
import domain.Orar;
import persistence.util.DataBaseConnection;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public class OrarRepository implements GenericRepository<Orar> {
    private static String insertSQL = "INSERT INTO orare(ora_inceput, ora_sfarsit) VALUES(?, ?)";
    private static String selectByIdSQL = "SELECT * FROM orare WHERE id_orar = ?";
    private Connection connection;

    private static volatile OrarRepository instance;

    private OrarRepository(){
        connection = DataBaseConnection.getInstance().getConnection();
    }

    public static OrarRepository getInstance() {
        if(instance == null){
            instance = new OrarRepository();
        }
        return instance;
    }

    private void insertZiOrar(Connection connection, long id_orar, int zi){
        String insertSQL = "INSERT INTO zile_orar(zi, id_orar) VALUES(?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL);
            stmt.setInt(1, zi);
            stmt.setLong(2, id_orar);

            stmt.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Orar save(Orar entity) {
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, entity.getOraInceput());
            stmt.setInt(2, entity.getOraSfarsit());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long insertedId = generatedKeys.getLong(1);
                    DayOfWeek[] zile = entity.getZile();
                    entity.setId(insertedId);
                    for(int i = 0; i < zile.length; i++){
                        if(zile[i] != null)
                            this.insertZiOrar(connection, insertedId, zile[i].getValue());
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public List<Orar> findAll() {
        return List.of();
    }

    private DayOfWeek [] getZileOrar(long id_orar){
        DayOfWeek [] zile = new DayOfWeek[7];
        try{
            PreparedStatement stmt = connection.prepareStatement("SELECT zi FROM zile_orar WHERE id_orar = ?");
            stmt.setLong(1, id_orar);
            ResultSet result = stmt.executeQuery();
            int i = 0;
            while (result.next()){
                zile[i] = DayOfWeek.of(result.getInt(1));
                i++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return zile;
    }

    @Override
    public Optional<Orar> findById(String id) {
        try{
            PreparedStatement stmt = connection.prepareStatement(selectByIdSQL);
            stmt.setLong(1, Long.parseLong(id));
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                int oraInceput = result.getInt(2);
                int oraSfarsit = result.getInt(3);
                DayOfWeek [] zile = getZileOrar(Long.parseLong(id));
                Orar orar = new Orar(zile, oraInceput, oraSfarsit);
                orar.setId(Long.parseLong(id));
                return Optional.of(orar);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Orar entity) {

    }

    @Override
    public void delete(Orar entity) {

    }
}
