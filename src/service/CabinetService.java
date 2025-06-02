package service;

import domain.Cabinet;
import persistence.CabinetRepository;

import java.util.Optional;

public class CabinetService {
    private final CabinetRepository cabinetRepository = CabinetRepository.getInstance();

    public Cabinet insertNewCabinet(String nume, int etaj, int numar) throws Exception{
        if(etaj < 0 || etaj > 20){
            throw new Exception("etaj invalid");
        }

        Cabinet cabinet = new Cabinet(nume, etaj, numar);
        return cabinetRepository.save(cabinet);
    }

    public Cabinet getCabinetById(long id){
        Optional<Cabinet> cabinet = cabinetRepository.findById(id + "");
        return cabinet.orElseThrow(() -> new RuntimeException("cabinetul dat lipseste"));
    }
}
