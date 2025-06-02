package service;

import domain.Orar;
import persistence.OrarRepository;

import java.util.Optional;

public class OrarService {
    private final OrarRepository orarRepository = OrarRepository.getInstance();

    public Orar insertNewOrar(String [] zile, int oraInceput, int oraSfarsit) throws Exception{
        if (oraInceput < 0 || oraInceput > 23){
            throw new Exception("ora de inceput nu este o ora normala");
        }

        if (oraSfarsit < 0 || oraSfarsit > 23 || oraInceput >= oraSfarsit){
            throw new Exception("ora de sfarsit nu este o ora normala");
        }

        for(String zi : zile) {
            if (!zi.equals("luni") && !zi.equals("marti") && !zi.equals("miercuri") && !zi.equals("joi") && !zi.equals("vineri") && !zi.equals("sambata") && !zi.equals("duminica")) {
                throw new Exception(zi + " nu este o zi a saptamani (luni, marti, miercuri, joi, vineri, sambata, duminica)");
            }
        }

        Orar orar = new Orar(zile, oraInceput, oraSfarsit);
        return orarRepository.save(orar);
    }

    public Orar getOrarById(Long id){
        Optional<Orar> orar = orarRepository.findById(id + "");
        return orar.orElseThrow(() -> new RuntimeException("Orarul dat lipseste"));
    }
}
