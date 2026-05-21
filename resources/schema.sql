DROP TABLE IF EXISTS Consultatie;
DROP TABLE IF EXISTS Programare;
DROP TABLE IF EXISTS Medic;
DROP TABLE IF EXISTS Client;

CREATE TABLE Client
(
    id INT PRIMARY KEY,
    nume VARCHAR(300) NOT NULL,
    email VARCHAR(100),
    telefon VARCHAR(20),
    tip_abonament VARCHAR(50),
    reducere_procent DOUBLE,
    pret_lunar DOUBLE
);

CREATE TABLE Medic
(
    id INT PRIMARY KEY,
    nume VARCHAR(300) NOT NULL,
    email VARCHAR(100),
    telefon VARCHAR(20),
    tip VARCHAR(100),
    specializare VARCHAR(200)
);

CREATE TABLE Programare
(
    cod_p VARCHAR(50) PRIMARY KEY,
    client_id INT NOT NULL,
    medic_id INT NOT NULL,
    data_ora DATETIME NOT NULL,
    pret_baza DOUBLE,
    status VARCHAR(20),
    FOREIGN KEY (client_id) REFERENCES Client(id),
    FOREIGN KEY (medic_id) REFERENCES Medic(id)
);

CREATE TABLE Consultatie
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    cod_p VARCHAR(50) NOT NULL,
    diagnostic TEXT,
    recomandari TEXT,
    cost_final DOUBLE,
    data_consultatie DATETIME,
    FOREIGN KEY (cod_p) REFERENCES Programare(cod_p)
);