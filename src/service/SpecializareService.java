package service;

import domain.Specializare;
import persistence.SpecializareRepository;

public class SpecializareService {
    private final SpecializareRepository adresaRepository = SpecializareRepository.getInstance();

    public Specializare insertNewSpecializare(String nume, String descriere, int salariu) throws Exception{
        Specializare adresa = new Specializare(nume, descriere, salariu);
        return adresaRepository.save(adresa);
    }
}
