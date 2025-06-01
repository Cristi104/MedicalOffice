import java.sql.*;
import java.time.DayOfWeek;
import java.util.Arrays;

public class Orar {
    protected long id;
    private DayOfWeek [] zile;
    private int oraInceput;
    private int oraSfarsit;

    public Orar(){
        this.zile = new DayOfWeek[5];
        this.zile[0] = DayOfWeek.MONDAY;
        this.zile[1] = DayOfWeek.TUESDAY;
        this.zile[2] = DayOfWeek.WEDNESDAY;
        this.zile[3] = DayOfWeek.THURSDAY;
        this.zile[4] = DayOfWeek.FRIDAY;
        this.oraInceput = 9;
        this.oraSfarsit = 17;
        this.id = -1;
    }

    public Orar(String [] zile, int oraInceput, int oraSfarsit) throws Exception{
        if (oraInceput < 0 || oraInceput > 23){
            throw new Exception("ora de inceput nu este o ora normala");
        }

        if (oraSfarsit < 0 || oraSfarsit > 23 || oraInceput >= oraSfarsit){
            throw new Exception("ora de sfarsit nu este o ora normala");
        }

        this.zile = new DayOfWeek[7];
        int i = 0;

        for(String zi : zile){
            if(!zi.equals("luni") && !zi.equals("marti") && !zi.equals("miercuri") && !zi.equals("joi") && !zi.equals("vineri") && !zi.equals("sambata") && !zi.equals("duminica")){
                throw new Exception(zi + " nu este o zi a saptamani (luni, marti, miercuri, joi, vineri, sambata, duminica)");
            }
            if(zi.equals("luni")) this.zile[i] = DayOfWeek.MONDAY;
            if(zi.equals("marti")) this.zile[i] = DayOfWeek.THURSDAY;
            if(zi.equals("miercuri")) this.zile[i] = DayOfWeek.WEDNESDAY;
            if(zi.equals("joi")) this.zile[i] = DayOfWeek.TUESDAY;
            if(zi.equals("vineri")) this.zile[i] = DayOfWeek.FRIDAY;
            if(zi.equals("sambata")) this.zile[i] = DayOfWeek.SATURDAY;
            if(zi.equals("duminica")) this.zile[i] = DayOfWeek.SUNDAY;
            i++;
        }

        this.oraInceput = oraInceput;
        this.oraSfarsit = oraSfarsit;
        this.id = -1;
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

    public long insertIntoDatabse(){
        Connection connection = DataBaseConnection.getInstance().getConnection();
        String insertSQL = "INSERT INTO orare(ora_inceput, ora_sfarsit) VALUES(?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.getOraInceput());
            stmt.setInt(2, this.getOraSfarsit());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long insertedId = generatedKeys.getLong(1);
                    DayOfWeek [] zile = this.getZile();
                    for(int i = 0; i < zile.length; i++){
                        this.insertZiOrar(connection, insertedId, zile[i].getValue());
                    }
                    this.id = insertedId;
                    return insertedId;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return -1;
    }

    public long getId() {
        return id;
    }

    public DayOfWeek[] getZile() {
        return zile;
    }

    public int getOraInceput() {
        return oraInceput;
    }

    public int getOraSfarsit() {
        return oraSfarsit;
    }
}
