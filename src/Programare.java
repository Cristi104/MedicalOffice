import java.time.LocalDateTime;

public class Programare {
    private Client client;
    private Medic medic;
    private LocalDateTime dateTime;
    private Operatie operatie;

    public Programare(Client client, Medic medic, LocalDateTime dateTime, Operatie serviciu) throws Exception{
        if(dateTime.isBefore(LocalDateTime.now())){
            throw new Exception("invalid date appointment cant be created in the past");
        }

        this.client = client;
        this.medic = medic;
        this.dateTime = dateTime;
        this.operatie = serviciu;
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
}
