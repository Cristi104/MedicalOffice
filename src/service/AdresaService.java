package service;

import domain.Adresa;
import persistence.AdresaRepository;

public class AdresaService {
    private final AdresaRepository adresaRepository = AdresaRepository.getInstance();

    public Adresa insertNewAdresa(String oras, String strada, int numar) throws Exception{
        if(numar < 0){
            throw new Exception("numarul nu poate fi negativ");
        }

        Adresa adresa = new Adresa(oras, strada, numar);
        return adresaRepository.save(adresa);
    }
}
