package service;

import domain.Adresa;
import domain.Cabinet;
import domain.Client;
import persistence.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository = ClientRepository.getInstance();

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
        return clientRepository.save(client);
    }

    public List<Client> getAllClienti(){
        return clientRepository.findAll();
    }

    public Client getClientById(long id){
        Optional<Client> client = clientRepository.findById(id + "");
        return client.orElseThrow(() -> new RuntimeException("clientul dat lipseste"));
    }

    public Client getClientByCNP(String CNP){
        Optional<Client> client = clientRepository.findByCNP(CNP);
        return client.orElseThrow(() -> new RuntimeException("clientul dat lipseste"));
    }
}
