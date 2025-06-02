package service;

import domain.*;
import persistence.MedicRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MedicService {
    private final MedicRepository medicRepository = MedicRepository.getInstance();

    public Medic insertNewMedic(String nume, String prenume, String telefon, Cabinet cabinet, Specializare specializare, Orar orar) throws Exception{

        Medic medic = new Medic(nume, prenume, telefon, cabinet, specializare, orar);
        return medicRepository.save(medic);
    }

    public List<Medic> getAllMedici(){
        return medicRepository.findAll();
    }

    public Medic getMedicById(long id){
        Optional<Medic> medic = medicRepository.findById(id + "");
        return medic.orElseThrow(() -> new RuntimeException("medicul dat lipseste"));
    }

    public List<Medic> getAllMediciLiberi(LocalDateTime dateTime){
        return medicRepository.findAll(dateTime);
    }
}
