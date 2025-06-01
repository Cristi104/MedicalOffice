package service;

import domain.Cabinet;
import domain.Medic;
import domain.Orar;
import domain.Specializare;
import persistence.MedicRepository;

public class MedicService {
    private final MedicRepository adresaRepository = MedicRepository.getInstance();

    public Medic insertNewMedic(String nume, String prenume, String telefon, Cabinet cabinet, Specializare specializare, Orar orar) throws Exception{

        Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, orar);
        return adresaRepository.save(medic);
    }
}
