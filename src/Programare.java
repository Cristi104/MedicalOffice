import java.time.LocalDateTime;

public class Programare {
    private Client client;
    private Medic medic;
    private LocalDateTime dateTime;
    private Serviciu serviciu;

    public Programare(Client client, Medic medic, LocalDateTime dateTime, Serviciu serviciu) throws Exception{
        if(dateTime.isBefore(LocalDateTime.now())){
            throw new Exception("invalid date appointment cant be created in the past");
        }

        this.client = client;
        this.medic = medic;
        this.dateTime = dateTime;
        this.serviciu = serviciu;
    }
}
