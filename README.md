# MedicalOffice

## Descriere

MedicalOffice este o aplicatie scrisa in java care ajuta medici sa gestineze informatia clientilor si programarile acestora

Actiuni posibile in cadrul aplicatiei:

1. Adauga un nou pacient
2. Adauga un nou medic
3. Creaza o progrmare
4. Anuleaza o progrmare
5. schimba data/ora unei programari
6. Afiseaza programarile pentru astazi ale unui doctor
7. Afiseaza programarile unui pacient
8. Afiseaza locurile libere ale unui medic intr-un interval
9. Afiseaza medici liberi la o anumita data/ora
10. Cauta un pacient dupa CNP sau nume si prenume

Clase folsite:

1. domain.Client(nume, prenume, CNP, telefon, adresa)
2. domain.Medic(nume, prenume, telefon, cabinet, orar, specializare)
3. domain.Programare(client, medic, data, ora, serviciu)
4. domain.Operatie(descriere, durata, cost)
5. domain.Adresa(oras, strada, numar)
6. domain.Orar(zile, interval_orar)
7. domain.Cabinet(nume, etaj, numar)
8. domain.Specializare(nume, descriere, salariu)
