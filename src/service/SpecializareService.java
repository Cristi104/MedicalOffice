package service;

import domain.Specializare;
import persistence.SpecializareRepository;

import java.util.Optional;

public class SpecializareService {
    private final SpecializareRepository specializareRepository = SpecializareRepository.getInstance();

    public Specializare insertNewSpecializare(String nume, String descriere, int salariu) throws Exception{
        Specializare adresa = new Specializare(nume, descriere, salariu);
        return specializareRepository.save(adresa);
    }

    public Specializare getSpecializareById(long id){
        Optional<Specializare> specializare = specializareRepository.findById(id + "");
        return specializare.orElseThrow(() -> new RuntimeException("Specializarea data lipseste"));
    }
}
