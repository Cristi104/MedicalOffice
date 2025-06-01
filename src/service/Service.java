package service;

import domain.Client;
import domain.Medic;
import domain.Operatie;
import domain.Programare;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        medic.insertIntoDatabse();
        this.medici.add(medic);
    }

    public void addClient(Client client){
        client.insertIntoDatabase();
        this.clienti.add(client);
    }

    public void addProgramare(Programare programare) throws Exception{
        if(!this.clienti.contains(programare.getClient())){
            throw new Exception("Clientul dat nu exista");
        }

        if(!this.medici.contains(programare.getMedic())){
            throw new Exception("Meidcul dat nu exista");
        }

        programare.insertIntoDatabase();

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
                System.out.println( "domain.Programare de la: " + programare.getDateTime().toString() +
                                    " pacient: " + programare.getClient().getNume() + " " + programare.getClient().getPrenume() +
                                    " operatie: " + programare.getOperatie().getDescriere() + "\n")
        );
    }

    public void printProgramari(Client client) throws Exception{
        for(Map.Entry<LocalDate, List<Programare>> entry : this.programari.entrySet()){
            entry.getValue().stream().filter(pro ->
                    pro.getClient().equals(client)
            ).forEach(programare ->
                    System.out.println( "domain.Programare de la: " + programare.getDateTime().toString() +
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
                continue;
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

                System.out.println("domain.Medic: " + medic.getNume() + " " + medic.getPrenume() + "\n");
            }
            return;
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

            System.out.println("domain.Medic: " + medic.getNume() + " " + medic.getPrenume() + "\n");
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

    public void printAllMedici(){
        for(int i = 0; i < this.medici.size(); i++){
            System.out.println(i + " - " + this.medici.get(i).toString() + "\n");
        }
    }

    public void printAllClienti(){
        for(int i = 0; i < this.clienti.size(); i++){
            System.out.println(i + " - " + this.clienti.get(i).toString() + "\n");
        }
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH/mm/dd/MM/yyyy");
            switch (opt){
                case 1:
                    try {
                        Client client = new Client(in, System.out);
                        this.addClient(client);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 2:
                    try {
                        Medic medic = new Medic(in, System.out);
                        this.addMedic(medic);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 3:
                    printAllMedici();
                    System.out.println("alegeti un medic:");
                    int indexMedic = in.nextInt();
                    printAllClienti();
                    System.out.println("alegeti un pacient:");
                    int indexPacient = in.nextInt();
                    in.nextLine();
                    System.out.println("data operatiei (HH/mm/dd/MM/yyyy):");
                    String dateString = in.nextLine();
                    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                    System.out.println("descriere operatie:");
                    String desc = in.nextLine();
                    System.out.println("durata operatie:");
                    int durata = in.nextInt();
                    System.out.println("cost operatie:");
                    int cost = in.nextInt();
                    try {
                        Operatie operatie = new Operatie(desc, durata, cost);
                        Programare programare = new Programare(this.clienti.get(indexPacient), this.medici.get(indexMedic), dateTime, operatie);
                        this.addProgramare(programare);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 4:
                    System.out.println("data operatiei (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    dateTime = LocalDateTime.parse(dateString, formatter);
                    printAllMedici();
                    System.out.println("alegeti un medic:");
                    indexMedic = in.nextInt();
                    LocalDate date = dateTime.toLocalDate();
                    try {
                        this.cancelProgramare(this.medici.get(indexMedic), dateTime);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 5:
                    System.out.println("data operatiei (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    dateTime = LocalDateTime.parse(dateString, formatter);
                    System.out.println("data noua (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    LocalDateTime newDateTime = LocalDateTime.parse(dateString, formatter);
                    printAllMedici();
                    System.out.println("alegeti un medic:");
                    indexMedic = in.nextInt();
                    try {
                        this.rescheduleProgramare(this.medici.get(indexMedic), dateTime, newDateTime);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 6:
                    printAllMedici();
                    System.out.println("alegeti un medic:");
                    indexMedic = in.nextInt();
                    try {
                        printProgramari(this.medici.get(indexMedic));
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 7:
                    printAllMedici();
                    System.out.println("alegeti un pacient:");
                    indexPacient = in.nextInt();
                    try {
                        printProgramari(this.clienti.get(indexPacient));
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 8:
                    System.out.println("data inceput (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    dateTime = LocalDateTime.parse(dateString, formatter);
                    System.out.println("data sfarsit (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    LocalDateTime dateTime1 = LocalDateTime.parse(dateString, formatter);
                    printAllMedici();
                    System.out.println("alegeti un medic:");
                    indexMedic = in.nextInt();
                    try {
                        this.printLiber(this.medici.get(indexMedic), dateTime.toLocalDate(), dateTime1.toLocalDate());
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 9:
                    System.out.println("data si ora ceruta (HH/mm/dd/MM/yyyy):");
                    dateString = in.nextLine();
                    dateTime = LocalDateTime.parse(dateString, formatter);
                    try {
                        this.printLiber(dateTime);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 10:
                    System.out.println("CNP:");
                    String CNP = in.nextLine();
                    try {
                        this.printPacient(CNP);
                    } catch (Exception e) {
                        System.out.println(e.toString() + "\n");
                    }
                    break;
                case 11:
                    ok = false;
                    break;
                default:
            }


        }
    }
}
