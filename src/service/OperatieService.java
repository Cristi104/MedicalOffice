package service;

import domain.Operatie;
import persistence.OperatieRepository;

public class OperatieService {
    private final OperatieRepository adresaRepository = OperatieRepository.getInstance();

    public Operatie insertNewOperatie(String descriere, float durata, int cost) throws Exception{
        if(durata <= 0 || durata >= 16){
            throw new Exception("durata invalida");
        }

        if(cost < 0){
            throw new Exception("cost invalid");
        }

        Operatie operatie = new Operatie(descriere, durata, cost);
        return adresaRepository.save(operatie);
    }
}
