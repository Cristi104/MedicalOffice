package service;

import domain.*;
import persistence.ProgramareRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProgramareService {
    private final ProgramareRepository programareRepository = ProgramareRepository.getInstance();

    public Programare insertNewProgramare(Client client, Medic medic, LocalDateTime dateTime, Operatie operatie) throws Exception{
        if(dateTime.isBefore(LocalDateTime.now())){
            throw new Exception("invalid date appointment cant be created in the past");
        }

        Programare programare = new Programare(client, medic, dateTime, operatie);
        return programareRepository.save(programare);
    }

    public List<Programare> getAllProgramari(){
        return programareRepository.findAll();
    }

    public void deleteProgramre(Programare programare){
        programareRepository.delete(programare);
    }

    public void updateProgramre(Programare programare){
        programareRepository.update(programare);
    }

    public List<Programare> getProgramariMedic(Medic medic, LocalDate date){
        return programareRepository.getProgramariMedic(medic, date);
    }

    public List<Programare> getProgramariClient(Client client){
        return programareRepository.getProgramariClient(client);
    }
}
