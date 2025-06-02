package domain;

import java.sql.*;
import java.time.LocalDateTime;

public class Programare {
    protected long id;
    private Client client;
    private Medic medic;
    private LocalDateTime dateTime;
    private Operatie operatie;

    public Programare(Client client, Medic medic, LocalDateTime dateTime, Operatie operatie) throws Exception{
        this.id = -1;
        this.client = client;
        this.medic = medic;
        this.dateTime = dateTime;
        this.operatie = operatie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public Medic getMedic() {
        return medic;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Operatie getOperatie() {
        return operatie;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Medic: " + medic.getNume() + " " + medic.getPrenume() + " Client: " + client.getNume() + " " + client.getPrenume() + " Data si ora: " + dateTime + " Operatie: " + operatie.getDescriere();
    }
}
