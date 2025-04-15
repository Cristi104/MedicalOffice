import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private List<Medic> medici;
    private List<Client> clienti;
    private Map<LocalDate, List<Programare>> programari;

    public Service(){
        this.medici = new ArrayList<>();
        this.clienti = new ArrayList<>();
        this.programari = new HashMap<>();
    }

    public void addMedic(Medic medic){
        this.medici.add(medic);
    }

    public void addClient(Client client){
        this.clienti.add(client);
    }

    public void addProgramare(Programare programare) throws Exception{
        if(!this.clienti.contains(programare.getClient())){
            throw new Exception("Clientul dat nu exista");
        }

        if(!this.medici.contains(programare.getMedic())){
            throw new Exception("Meidcul dat nu exista");
        }

        LocalDate date = programare.getDateTime().toLocalDate();
        List<Programare> programari = this.programari.get(date);

        if(null == programari){
            programari = new ArrayList<>();
            this.programari.put(date, programari);
        }

        programari.add(programare);
    }

    public void cancelProgramare(Medic medic, LocalDateTime dateTime) throws Exception{
        LocalDate date = dateTime.toLocalDate();
        List<Programare> programari = this.programari.get(date);

        if(null == programari){
            throw new Exception("nu exista programari in ziua data");
        }

        int index = programari.indexOf(
                programari.stream().filter(pro ->
                        pro.getMedic().equals(medic) && pro.getDateTime().equals(dateTime)
                ).findFirst().orElse(null));

        if(index != -1){
            programari.remove(index);
        } else {
            throw new Exception("Nu exista programarea cu informatiile specificate");
        }
    }

    public void rescheduleProgramare(Medic medic, LocalDateTime oldDateTime, LocalDateTime newDateTile) throws Exception{
        LocalDate date = oldDateTime.toLocalDate();
        List<Programare> programari = this.programari.get(date);

        if(null == programari){
            throw new Exception("nu exista programari in ziua data");
        }

        int index = programari.indexOf(
                programari.stream().filter(pro ->
                        pro.getMedic().equals(medic) && pro.getDateTime().equals(oldDateTime)
                ).findFirst().orElse(null));

        if (index == -1) {
            throw new Exception("Nu exista programarea cu informatiile specificate");
        }

        Programare programare = programari.get(index);

        if (!oldDateTime.toLocalDate().equals(newDateTile.toLocalDate())){
            programari.remove(index);
        }

        programare.setDateTime(newDateTile);

        if (!oldDateTime.toLocalDate().equals(newDateTile.toLocalDate())){
            this.addProgramare(programare);
        }

    }
}
