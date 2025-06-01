package service;

import domain.Cabinet;
import persistence.CabinetRepository;

public class CabinetService {
    private final CabinetRepository adresaRepository = CabinetRepository.getInstance();

    public Cabinet insertNewCabinet(String nume, int etaj, int numar) throws Exception{
        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        Cabinet cabinet = new Cabinet(nume, etaj, numar);
        return adresaRepository.save(cabinet);
    }
}
