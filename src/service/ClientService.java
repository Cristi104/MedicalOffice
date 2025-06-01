package service;

import domain.Adresa;
import domain.Client;
import persistence.ClientRepository;

public class ClientService {
    private final ClientRepository adresaRepository = ClientRepository.getInstance();

    public Client insertNewClient(String nume, String prenume, String telefon, String CNP, Adresa adresa) throws Exception{
        if(CNP.length() != 13){
            throw new Exception("CNP invalid");
        }

        for(int i = 0; i < CNP.length(); i++){
            if(!Character.isDigit(CNP.charAt(i))){
                throw new Exception("CNP invalid");
            }
        }

        Client client = new Client(nume, prenume, telefon, CNP, adresa);
        return adresaRepository.save(client);
    }
}
