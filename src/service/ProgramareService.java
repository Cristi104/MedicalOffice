package service;

import domain.*;
import persistence.ProgramareRepository;

import java.time.LocalDateTime;

public class ProgramareService {
    private final ProgramareRepository adresaRepository = ProgramareRepository.getInstance();

    public Programare insertNewProgramare(Client client, Medic medic, LocalDateTime dateTime, Operatie operatie) throws Exception{
        if(dateTime.isBefore(LocalDateTime.now())){
            throw new Exception("invalid date appointment cant be created in the past");
        }

        Programare programare = new Programare(client, medic, dateTime, operatie);
        return adresaRepository.save(programare);
    }
}
