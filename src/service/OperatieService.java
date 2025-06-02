package service;

import domain.Operatie;
import persistence.OperatieRepository;

import java.util.Optional;

public class OperatieService {
    private final OperatieRepository operatieRepository = OperatieRepository.getInstance();

    public Operatie insertNewOperatie(String descriere, float durata, int cost) throws Exception{
        if(durata <= 0 || durata >= 16){
            throw new Exception("durata invalida");
        }

        if(cost < 0){
            throw new Exception("cost invalid");
        }

        Operatie operatie = new Operatie(descriere, durata, cost);
        return operatieRepository.save(operatie);
    }

    public Operatie getClientById(long id){
        Optional<Operatie> operatie = operatieRepository.findById(id + "");
        return operatie.orElseThrow(() -> new RuntimeException("operatia data lipseste"));
    }
}
