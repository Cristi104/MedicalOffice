import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

    public void printProgramari(Medic medic) throws Exception{
        LocalDate date = LocalDate.now();
        List<Programare> programari = this.programari.get(date);

        if(null == programari) {
            throw new Exception("nu exista programari in ziua data");
        }

        programari.stream().filter(pro ->
                pro.getMedic().equals(medic)
        ).forEach(programare ->
                System.out.println( "Programare de la: " + programare.getDateTime().toString() +
                                    " pacient: " + programare.getClient().getNume() + " " + programare.getClient().getPrenume() +
                                    " operatie: " + programare.getOperatie().getDescriere() + "\n")
        );
    }

    public void printProgramari(Client client) throws Exception{
        for(Map.Entry<LocalDate, List<Programare>> entry : this.programari.entrySet()){
            entry.getValue().stream().filter(pro ->
                    pro.getClient().equals(client)
            ).forEach(programare ->
                    System.out.println( "Programare de la: " + programare.getDateTime().toString() +
                                        " medic: " + programare.getMedic().getNume() + " " + programare.getMedic().getPrenume() +
                                        " operatie: " + programare.getOperatie().getDescriere() + "\n")
            );
        }
    }

    public void printLiber(Medic medic, LocalDate dateInceput, LocalDate dateTimeSfarsit){
        for(LocalDate date = dateInceput; date.isBefore(dateTimeSfarsit.plusDays(1)); date = date.plusDays(1)){
            List<Programare> programari = this.programari.get(date);
            if(Arrays.binarySearch(medic.getOrar().getZile(), date.getDayOfWeek()) < 0){
                continue;
            }

            if(null == programari) {
                System.out.println("Ziua: " + date.toString() + " de la: " + medic.getOrar().getOraInceput() + " la: " + medic.getOrar().getOraSfarsit() + "\n");
            }

            boolean [] ore = new boolean[24];
            for(int i = medic.getOrar().getOraInceput(); i < medic.getOrar().getOraSfarsit(); i++){
                ore[i] = true;
            }

            programari.stream().filter(pro ->
                    pro.getMedic().equals(medic)
            ).forEach(programare -> {
                for (int j = programare.getDateTime().getHour(); j < programare.getDateTime().getHour() + programare.getOperatie().getDurata(); j++) {
                    ore[j] = false;
                }
            });
            System.out.println("Ziua: " + date.toString());

            boolean helper = false;
            for (int i = 0; i < ore.length; i++) {
                if (ore[i]){
                    if(!helper)
                        System.out.println(" de la: " + i);
                } else {
                    if(helper)
                        System.out.println(" la: " + i);
                }
                helper = ore[i];
            }
            System.out.println("\n");
        }
    }

    public void printLiber(LocalDateTime dateTime){
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        List<Programare> programari = this.programari.get(date);

        if(null == programari) {
            for(Medic medic : this.medici){
                if(Arrays.binarySearch(medic.getOrar().getZile(), date.getDayOfWeek()) < 0){
                    continue;
                }

                if(time.getHour() < medic.getOrar().getOraInceput() || time.getHour() >= medic.getOrar().getOraSfarsit()){
                    continue;
                }

                System.out.println("Medic: " + medic.getNume() + " " + medic.getPrenume() + "\n");
            }
        }

        for(Medic medic : this.medici){
            if(Arrays.binarySearch(medic.getOrar().getZile(), date.getDayOfWeek()) < 0){
                continue;
            }

            if(time.getHour() < medic.getOrar().getOraInceput() || time.getHour() >= medic.getOrar().getOraSfarsit()){
                continue;
            }

            assert programari != null;
            if(programari.stream().filter(pro ->
                    pro.getMedic().equals(medic) && (pro.getDateTime().getHour() <= time.getHour() && pro.getDateTime().getHour() + pro.getOperatie().getDurata() > time.getHour())
            ).findFirst().orElse(null) != null){
                continue;
            }

            System.out.println("Medic: " + medic.getNume() + " " + medic.getPrenume() + "\n");
        }
    }

    public void printPacient(String CNP) throws Exception{
        Client client = this.clienti.stream().filter(cli ->
                cli.getCNP().equals(CNP)
        ).findFirst().orElse(null);

        if(client == null){
            throw new Exception("CNP-ul dat nu corespunde nici unui pacient");
        }

        System.out.println("Nume: " + client.getNume() + " " + client.getPrenume() + " nr_telefon: " + client.getTelefon() + " adresa: " + client.getAdresa());
    }

    public void menu() throws Exception {
        boolean ok = true;
        Scanner in = new Scanner(System.in);
        while (ok){
            System.out.println("" +
                    "1 - adauga un pacient nou\n" +
                    "2 - adauga un medic nou\n" +
                    "3 - creaza o programare\n" +
                    "4 - anuleaza o programare\n" +
                    "5 - schimba ora/data unei programari\n" +
                    "6 - afiseaza programarile de astazi ale unui medic\n" +
                    "7 - afiseaza programarile unui pacient\n" +
                    "8 - afiseaza locurile libere ale unui medic intr-un interval\n" +
                    "9 - afiseaza medici liberi la o anumita data/ora\n" +
                    "10 - cauta un pacient dupa CNP\n" +
                    "11 - iesire");
            int opt = in.nextInt();
            in.nextLine();
            switch (opt){
                case 1:
                    System.out.println("Nume:");
                    String nume = in.nextLine();
                    System.out.println("Prenume:");
                    String prenume = in.nextLine();
                    System.out.println("Nr Telefon:");
                    String telefon = in.nextLine();
                    System.out.println("CNP:");
                    String CNP = in.nextLine();
                    System.out.println("Oras:");
                    String oras = in.nextLine();
                    System.out.println("Strada:");
                    String strada = in.nextLine();
                    System.out.println("Nr:");
                    int nr = in.nextInt();
                    in.nextLine();
                    Adresa adresa = new Adresa(oras, strada, nr);
                    Client client = new Client(nume, prenume, telefon, CNP, adresa);
                    this.addClient(client);
                    break;
                case 2:
                    System.out.println("Nume:");
                    nume = in.nextLine();
                    System.out.println("Prenume:");
                    prenume = in.nextLine();
                    System.out.println("Nr Telefon:");
                    telefon = in.nextLine();
                    Cabinet cabinet = new Cabinet("test", 1, 101);
                    System.out.println("Specializare:");
                    String specializare = in.nextLine();
                    Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, new Orar());
                    this.addMedic(medic);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    ok = false;
                    break;
                default:
            }


        }
    }
}
