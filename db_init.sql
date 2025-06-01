DROP TABLE programari;
DROP TABLE operatii;
DROP TABLE medici;
DROP TABLE specializari;
DROP TABLE zile_orar;
DROP TABLE orare;
DROP TABLE cabinete;
DROP TABLE clienti;
DROP TABLE adrese;

CREATE TABLE adrese(
    id_adresa INTEGER PRIMARY KEY AUTO_INCREMENT,
    oras VARCHAR(32),
    strada VARCHAR(32),
    numar INTEGER
);

CREATE TABLE clienti(
    id_client INTEGER PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(32),
    prenume VARCHAR(32),
    CNP VARCHAR(13),
    telefon VARCHAR(10),
    id_adresa INTEGER,
    FOREIGN KEY(id_adresa) REFERENCES adrese(id_adresa)
);

CREATE TABLE cabinete(
    id_cabinet INTEGER PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(32),
    etaj INTEGER,
    numar INTEGER
);

CREATE TABLE orare(
    id_orar INTEGER PRIMARY KEY AUTO_INCREMENT,
    ora_inceput INTEGER,
    ora_sfarsit INTEGER
);

CREATE TABLE zile_orar(
    zi INTEGER,
    id_orar INTEGER,
    FOREIGN KEY(id_orar) REFERENCES orare(id_orar),
    PRIMARY KEY (zi, id_orar)
);

CREATE TABLE specializari(
    id_specializare INTEGER PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(32),
    descriere VARCHAR(128),
    salariu FLOAT
);

CREATE TABLE medici(
    id_medic INTEGER PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(32),
    prenume VARCHAR(32),
    telefon VARCHAR(10),
    id_cabinet INTEGER,
    id_orar INTEGER,
    id_specializare INTEGER,
    FOREIGN KEY(id_cabinet) REFERENCES cabinete(id_cabinet),
    FOREIGN KEY(id_orar) REFERENCES orare(id_orar),
    FOREIGN KEY(id_specializare) REFERENCES specializari(id_specializare)
);

CREATE TABLE operatii(
    id_operatie INTEGER PRIMARY KEY AUTO_INCREMENT,
    descriere VARCHAR(128),
    durata FLOAT,
    cost INTEGER
);

CREATE TABLE programari(
    id_programare INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_client INTEGER,
    id_medic INTEGER,
    data DATETIME,
    id_operatie INTEGER,
    FOREIGN KEY(id_client) REFERENCES clienti(id_client),
    FOREIGN KEY(id_medic) REFERENCES medici(id_medic),
    FOREIGN KEY(id_operatie) REFERENCES operatii(id_operatie)
);