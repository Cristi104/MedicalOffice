package view;

import domain.*;
import service.*;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientApp {
    private AdresaService adresaService = new AdresaService();
    private CabinetService cabinetService = new CabinetService();
    private ClientService clientService = new ClientService();
    private MedicService medicService = new MedicService();
    private OperatieService operatieService = new OperatieService();
    private OrarService orarService = new OrarService();
    private ProgramareService programareService = new ProgramareService();
    private SpecializareService specializareService = new SpecializareService();
    private Scanner in = new Scanner(System.in);
    private PrintStream out = System.out;
    private AuditService audit = new AuditService("output.csv");

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
            while (true){
                app.menu();
                int option =  Integer.parseInt(app.in.nextLine());
                app.execute(option);
            }
    }

    private void menu(){
        this.out.println("\n" +
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
                    "11 - iesire\n");

    }

    private void execute(int option){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH/mm/dd/MM/yyyy");
        switch (option){
            case 1:
                this.addClient();
                break;
            case 2:
                this.addMedic();
                break;
            case 3:
                this.addProgramare();
                break;
            case 4:
                this.cancelProgramare();
                break;
            case 5:
                this.updateProgramare();
                break;
            case 6:
                this.printProgramariMedic();
                break;
            case 7:
                this.printProgramariClient();
                break;
            case 8:
                this.printLiberInterval();
                break;
            case 9:
                this.printLiberMedic();
                break;
            case 10:
                this.cautaClient();
                break;
            case 11:
                audit.close();
                in.close();
                System.exit(0);
                break;
            default:
        }
    }

    private void addClient(){
        out.print("Nume: ");
        String nume = in.nextLine();
        out.print("Prenume: ");
        String prenume = in.nextLine();
        out.print("Telefon: ");
        String telefon = in.nextLine();
        out.print("CNP: ");
        String CNP = in.nextLine();

        out.print("Adresa: \n");
        out.print("Oras: ");
        String oras = in.nextLine();
        out.print("Strada: ");
        String strada = in.nextLine();
        out.print("numar: ");
        int numar = Integer.parseInt(in.nextLine());

        Adresa adresa;
        try {
            adresa = adresaService.insertNewAdresa(oras, strada, numar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Client client;
        try {
            client = clientService.insertNewClient(nume, prenume, telefon, CNP, adresa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        out.print("Pacient nou adaugat\n");
        audit.write("Pacient nou adaugat", client.toString());
    }

    private void addMedic(){
        out.print("Nume: ");
        String nume = in.nextLine();
        out.print("Prenume: ");
        String prenume = in.nextLine();
        out.print("Telefon: ");
        String telefon = in.nextLine();

        out.print("Cabinet: \n");
        out.print("Nume: ");
        String numeCabinet = in.nextLine();
        out.print("Etaj: ");
        int etaj = Integer.parseInt(in.nextLine());
        out.print("Numar: ");
        int numar = Integer.parseInt(in.nextLine());
        Cabinet cabinet;
        try {
            cabinet = cabinetService.insertNewCabinet(numeCabinet, etaj, numar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        out.print("Specializare: \n");
        out.print("Nume: ");
        String numeSpecializare = in.nextLine();
        out.print("Descriere: ");
        String descriere = in.nextLine();
        out.print("Salariu: ");
        int salariu = Integer.parseInt(in.nextLine());
        Specializare specializare;
        try {
            specializare = specializareService.insertNewSpecializare(numeSpecializare, descriere, salariu);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        out.print("Orar: \n");
        out.print("Ora Inceput: ");
        int oraInceput = Integer.parseInt(in.nextLine());
        out.print("Ora Sfarsit: ");
        int oraSfarsit = Integer.parseInt(in.nextLine());
        out.print("Zile(exemplu: \"luni marti miercuri joi vineri\"): ");
        String [] zile = in.nextLine().trim().split(" ");

        Orar orar;
        try {
            orar = orarService.insertNewOrar(zile, oraInceput, oraSfarsit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Medic medic;
        try {
            medic = medicService.insertNewMedic(nume, prenume, telefon, cabinet, specializare, orar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        out.print("Medic nou adaugat\n");
        audit.write("Medic nou adaugat", medic.toString());
    }

    private void addProgramare(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH/mm/dd/MM/yyyy");
        List<Medic> medicList = medicService.getAllMedici();
        for(int i = 0; i < medicList.size(); i++){
            out.println(i + " - " + medicList.get(i));
        }

        out.print("Alegeti un medic: ");
        int nr_medic = Integer.parseInt(in.nextLine());
        if(nr_medic == -1)
            return;

        List<Client> clientList = clientService.getAllClienti();
        for(int i = 0; i < clientList.size(); i++){
            out.println(i + " - " + clientList.get(i));
        }

        out.print("Alegeti un client: ");
        int nr_client = Integer.parseInt(in.nextLine());
        if(nr_client == -1)
            return;

        out.print("Data programarii (HH/MM/dd/mm/yyyy): ");
        LocalDateTime dateTime = LocalDateTime.parse(in.nextLine().trim(), formatter);

        out.print("Operatie: \n");
        out.print("Descriere: ");
        String descriere = in.nextLine();
        out.print("Durata: ");
        float durata = Float.parseFloat(in.nextLine());
        out.print("Cost: ");
        int cost = Integer.parseInt(in.nextLine());

        Operatie operatie;
        try {
            operatie = operatieService.insertNewOperatie(descriere, durata, cost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Programare programare;
        try {
            programare = programareService.insertNewProgramare(clientList.get(nr_client), medicList.get(nr_medic), dateTime, operatie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        out.println("Programare creata");
        audit.write("Programare creata", programare.toString());
    }

    private void cancelProgramare(){
        List<Programare> programari = programareService.getAllProgramari();
        for(int i = 0; i < programari.size(); i++){
            out.println(i + " - " + programari.get(i));
        }

        out.print("Alegeti o programare: ");
        int nr_programare = Integer.parseInt(in.nextLine());
        if(nr_programare == -1)
            return;

        programareService.deleteProgramre(programari.get(nr_programare));
        out.println("Programare anulata");
        audit.write("Programare anulata", programari.get(nr_programare).toString());
    }

    private void updateProgramare(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH/mm/dd/MM/yyyy");
        List<Programare> programari = programareService.getAllProgramari();
        for(int i = 0; i < programari.size(); i++){
            out.println(i + " - " + programari.get(i));
        }

        out.print("Alegeti o programare: ");
        int nr_programare = Integer.parseInt(in.nextLine());
        if(nr_programare == -1)
            return;

        out.print("Data programarii (HH/MM/dd/mm/yyyy): ");
        LocalDateTime dateTime = LocalDateTime.parse(in.nextLine().trim(), formatter);

        programari.get(nr_programare).setDateTime(dateTime);
        programareService.updateProgramre(programari.get(nr_programare));
        out.println("Programare updatata");
        audit.write("Programare updatata", programari.get(nr_programare).toString());
    }

    private void printProgramariMedic(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Medic> medicList = medicService.getAllMedici();
        for(int i = 0; i < medicList.size(); i++){
            out.println(i + " - " + medicList.get(i));
        }

        out.print("Alegeti un medic: ");
        int nr_medic = Integer.parseInt(in.nextLine());
        if(nr_medic == -1)
            return;

        out.print("Data programarii (dd/mm/yyyy): ");
        LocalDate date = LocalDate.parse(in.nextLine().trim(), formatter);

        List<Programare> programari = programareService.getProgramariMedic(medicList.get(nr_medic), date);
        for(int i = 0; i < programari.size(); i++){
            out.println(i + " - " + programari.get(i));
        }
        audit.write("Afisare programari medic", "");
    }

    private void printProgramariClient(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Client> clientList = clientService.getAllClienti();
        for(int i = 0; i < clientList.size(); i++){
            out.println(i + " - " + clientList.get(i));
        }

        out.print("Alegeti un client: ");
        int nr_client = Integer.parseInt(in.nextLine());
        if(nr_client == -1)
            return;

        List<Programare> programari = programareService.getProgramariClient(clientList.get(nr_client));
        for(int i = 0; i < programari.size(); i++){
            out.println(i + " - " + programari.get(i));
        }
        audit.write("Afisare programari client", "");
    }

    private void printLiberInterval(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Medic> medicList = medicService.getAllMedici();
        for(int i = 0; i < medicList.size(); i++){
            out.println(i + " - " + medicList.get(i));
        }

        out.print("Alegeti un medic: ");
        int nr_medic = Integer.parseInt(in.nextLine());
        if(nr_medic == -1)
            return;

        out.print("Data inceput (dd/mm/yyyy): ");
        LocalDate dateInceput = LocalDate.parse(in.nextLine().trim(), formatter);
        out.print("Data sfarsit (dd/mm/yyyy): ");
        LocalDate dateSfarsit = LocalDate.parse(in.nextLine().trim(), formatter);

        Medic medic = medicList.get(nr_medic);
        for(LocalDate date = dateInceput; date.isBefore(dateSfarsit); date = date.plusDays(1)){
            List<Programare> programari = programareService.getProgramariMedic(medic, date);
            try{
                if(Arrays.binarySearch(medic.getOrar().getZile(), date.getDayOfWeek()) < 0){
                    continue;
                }
            } catch (Exception e){
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
        }
        audit.write("Afisare intervale libere medic", "");
    }

    private void printLiberMedic(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH/mm/dd/MM/yyyy");
        out.print("Data (HH/MM/dd/mm/yyyy): ");
        LocalDateTime dateTime = LocalDateTime.parse(in.nextLine().trim(), formatter);

        List<Medic> medicList = medicService.getAllMediciLiberi(dateTime);
        for(int i = 0; i < medicList.size(); i++){
            out.println(i + " - " + medicList.get(i));
        }

        audit.write("Afisare medici liber in interval", "");
    }

    private void cautaClient(){
        out.print("CNP: ");
        String CNP = in.nextLine();

        Client client = clientService.getClientByCNP(CNP);
        out.println(client);
        audit.write("Cautare client dupa CNP", client.toString());
    }
}
