DROP DATABASE IF EXISTS ebalance;
CREATE DATABASE ebalance;
USE ebalance;

CREATE TABLE Amministratore
(
    IdAmministratore int NOT NULL AUTO_INCREMENT,
    Nome varchar(50) NOT NULL,
    Cognome varchar(50) NOT NULL,
    DataNascita date NOT NULL,
    Email varchar(50) NOT NULL,
    Password varchar(50) NOT NULL,
    FlagTipo bit NOT NULL, --  0 = "Amministratore registrato" 1 = "Super-Admin"
    PRIMARY KEY(IdAmministratore)
);

CREATE TABLE Contratto
(
    IdContratto int NOT NULL AUTO_INCREMENT,
    NomeEnte varchar(50) NOT NULL,
    ConsumoMedioAnnuale float NOT NULL,
    CostoMedioUnitario float NOT NULL,
    DataSottoscrizione date NOT NULL,
    Durata int NOT NULL,
    PrezzoVendita float NOT NULL,
    IdAmministratore int,
    PRIMARY KEY(IdContratto),
    FOREIGN KEY(IdAmministratore) REFERENCES Amministratore(IdAmministratore) ON UPDATE cascade ON DELETE SET NULL
);

CREATE TABLE Report
(
    IdReport int NOT NULL AUTO_INCREMENT,
    DataEmissione date NOT NULL,
    IdAmministratore int,
    NomeReport varchar(20) NOT NULL,
    PRIMARY KEY(IdReport),
    FOREIGN KEY(IdAmministratore) REFERENCES Amministratore(IdAmministratore) ON UPDATE cascade ON DELETE SET NULL
);

CREATE TABLE Vendita
(
    IdVendita int NOT NULL AUTO_INCREMENT,
    EnergiaVenduta float NOT NULL,
    DataVendita date NOT NULL,
    RicavoTotale float NOT NULL,
    IdAmministratore int,
    PRIMARY KEY(IdVendita),
    FOREIGN KEY(IdAmministratore) REFERENCES Amministratore(IdAmministratore) ON UPDATE cascade ON DELETE SET NULL
);

CREATE TABLE Batteria
(
    IdBatteria int NOT NULL AUTO_INCREMENT,
    FlagStatoBatteria bit NOT NULL,  --  0 = "Batteria non funzionante/guasta" 1 = "Batteria funzionante"
    CapacitaMax float NOT NULL,
    PercentualeCarica float NOT NULL CHECK (PercentualeCarica >= 0 AND PercentualeCarica <= 100),
    PRIMARY KEY(IdBatteria)
);

CREATE TABLE Disporre
(
    IdBatteria int NOT NULL,
    IdVendita int NOT NULL,
    FOREIGN KEY(IdBatteria) REFERENCES Batteria(IdBatteria) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(IdVendita) REFERENCES Vendita(IdVendita) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE ConsumoEdificio
(
    IdEdificio int NOT NULL AUTO_INCREMENT,
    NomeEdificio varchar(20) NOT NULL,
    ConsumoAttuale float NOT NULL,
    PRIMARY KEY(IdEdificio)
);

CREATE TABLE ArchivioConsumo
(
    IdConsumo int NOT NULL AUTO_INCREMENT,
    DataConsumo date NOT NULL,
    ConsumoGiornaliero float NOT NULL,
    IdEdificio int NOT NULL,
    PRIMARY KEY(IdConsumo),
    FOREIGN KEY(IdEdificio) REFERENCES ConsumoEdificio(IdEdificio) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Utilizza
(
    IdBatteria int NOT NULL,
    IdEdificio int NOT NULL,
    FOREIGN KEY(IdBatteria) REFERENCES Batteria(IdBatteria) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(IdEdificio) REFERENCES ConsumoEdificio(IdEdificio) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE TipoSorgente
(
    Tipo varchar(30) NOT NULL,
    PRIMARY KEY(Tipo)
);

CREATE TABLE Sorgente
(
    IdSorgente int NOT NULL AUTO_INCREMENT,
    Tipologia varchar(30) NOT NULL,
    DataInstallazione date NOT NULL,
    ProduzioneAttuale float NOT NULL,
    FlagStatoSorgente bit NOT NULL,  --  0 = "Sorgente non funzionante/guasta" 1 = "Sorgente funzionante"
    FlagAttivazioneSorgente bit NOT NULL, --  0 = "Sorgente non in funzione" 1 = "Sorgente in funzione",
    PRIMARY KEY(IdSorgente),
    FOREIGN KEY(Tipologia) REFERENCES TipoSorgente(Tipo) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE ArchivioProduzione
(
    IdProduzione int NOT NULL AUTO_INCREMENT,
    DataProduzione date NOT NULL,
    ProduzioneGiornaliera float NOT NULL,
    IdSorgente int NOT NULL,
    PRIMARY KEY(IdProduzione),
    FOREIGN KEY(IdSorgente) REFERENCES Sorgente(IdSorgente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Caricare
(
    IdSorgente int NOT NULL,
    IdBatteria int NOT NULL,
    FOREIGN KEY(IdSorgente) REFERENCES Sorgente(IdSorgente) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(IdBatteria) REFERENCES Batteria(IdBatteria) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE CondizioneMeteo
(
    Condizione varchar(20) NOT NULL,
    PRIMARY KEY(Condizione)
);

CREATE TABLE Meteo
(
    IdMeteo int NOT NULL AUTO_INCREMENT,
    DataRilevazione date NOT NULL,
    OraRilevazione time NOT NULL,
    VelocitaVento float NOT NULL,
    ProbabilitaPioggia int NOT NULL,
    CondizioniMetereologiche varchar(20) NOT NULL,
    PRIMARY KEY(IdMeteo),
    FOREIGN KEY(CondizioniMetereologiche) REFERENCES CondizioneMeteo(Condizione) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE Influenzare
(
    IdMeteo int NOT NULL,
    IdSorgente int NOT NULL,
    FOREIGN KEY(IdMeteo) REFERENCES Meteo(IdMeteo) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(IdSorgente) REFERENCES Sorgente(IdSorgente) ON UPDATE cascade ON DELETE cascade
);

CREATE TABLE ParametriIA
(
    IdParametro int NOT NULL AUTO_INCREMENT,
    Piano varchar(50) NOT NULL,
    FlagAttivazioneParametro bit NOT NULL, --  0 = "ParametroIA disattivato" 1 = "ParametroIA attivo"
    IdAmministratore int,
    PRIMARY KEY(IdParametro),
    FOREIGN KEY(IdAmministratore) REFERENCES Amministratore(IdAmministratore) ON UPDATE cascade ON DELETE SET NULL
);

CREATE TABLE Interagisce
(
    IdParametro int NOT NULL,
    TipoSorgente varchar(30) NOT NULL,
    FlagPreferenzaSorgente bit NOT NULL, -- 0 = "Sorgente non preferita" 1 = "Sorgente Preferita"
    PercentualeUtilizzoSorgente int NOT NULL,
    PrioritaSorgente int NOT NULL,
    FOREIGN KEY(IdParametro) REFERENCES ParametriIA(IdParametro) ON UPDATE cascade ON DELETE cascade,
    FOREIGN KEY(TipoSorgente) REFERENCES TipoSorgente(Tipo) ON UPDATE cascade ON DELETE cascade
);

INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Matteo", "Ercolino", "1999-01-01", "m.ercolino1@studenti.unisa.it", "Matteo2024!", 1);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Simone", "Silvestri", "2001-06-15", "s.silvestri15@studenti.unisa.it", "Simone2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email,Password, FlagTipo)
VALUES ( "Simone", "Cirma", "2001-04-24", "s.cirma@studenti.unisa.it", "Simone2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Donato", "Folgieri", "2000-05-17", "d.folgieri@studenti.unisa.it", "Donato2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Antonio", "Di Giorgio", "2000-12-05", "a.digiorgio8@studenti.unisa.it", "Antonio2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Daniela", "Palma", "2002-09-16", "d.palma11@studenti.unisa.it", "Daniela2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Emanuele", "Vitale", "2002-08-11", "e.vitale23@studenti.unisa.it", "Emanuele2024!", 0);
INSERT INTO Amministratore( Nome, Cognome, DataNascita, Email, Password, FlagTipo)
VALUES ( "Mario", "De Luca", "2001-04-24", "m.deluca99@studenti.unisa.it", "Mario2024!", 0);

INSERT INTO Contratto( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)
VALUES ("Enel Energia", 1000000, 0.05, "2000-01-01", 120, 0.03, 03);
INSERT INTO Contratto( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)
VALUES ("Edison", 5000000, 0.08, "2010-01-01", 120, 0.05, 05);
INSERT INTO Contratto( NomeEnte, ConsumoMedioAnnuale, CostoMedioUnitario, DataSottoscrizione, Durata, PrezzoVendita, IdAmministratore)
VALUES ("Enel Energia", 6000000, 0.12, "2020-01-01", 120, 0.09, 01);

INSERT INTO Report(DataEmissione, IdAmministratore, NomeReport)
VALUES ("2021-12-31", 03, "Report1.pdf");
INSERT INTO Report(DataEmissione, IdAmministratore, NomeReport)
VALUES ("2022-12-31", 04, "Report2.pdf");
INSERT INTO Report(DataEmissione, IdAmministratore, NomeReport)
VALUES ("2023-12-31", 05, "Report3.pdf");


INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(5000, "2021-04-21", 540, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(3500, "2021-06-23", 315, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(1000, "2021-10-10", 90, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(5000, "2022-02-23", 450, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(6000, "2022-04-10", 540, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(10000, "2022-08-08", 900, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(20000, "2023-08-01", 1800, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(3000, "2023-09-07", 270, 01);
INSERT INTO Vendita(EnergiaVenduta, DataVendita, RicavoTotale, IdAmministratore)
VALUES(2500, "2023-11-19", 225, 01);


INSERT INTO Batteria(FlagStatoBatteria, CapacitaMax, PercentualeCarica)
VALUES(1, 7000, 33);
INSERT INTO Batteria(FlagStatoBatteria, CapacitaMax, PercentualeCarica)
VALUES(1, 7000, 27);
INSERT INTO Batteria(FlagStatoBatteria, CapacitaMax, PercentualeCarica)
VALUES(1, 7000, 30);
INSERT INTO Batteria(FlagStatoBatteria, CapacitaMax, PercentualeCarica)
VALUES(0, 7000, 0);

INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(01, 01);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(02, 01);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(03, 01);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(03, 02);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(01, 03);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(02, 03);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(01, 04);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(02, 04);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(03, 04);
INSERT INTO Disporre(IdBatteria, IdVendita)
VALUES(02, 05);

INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("A1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("A2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("A3", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("B", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("B1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("B2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("C", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("C1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("C2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("D", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("D1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("D2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("D3", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("E", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("E1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("E2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("F", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("F1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("F2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("F3", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("G", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("H", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("I1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L3", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L4", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L5", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L6", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("L7", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("M1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("M2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("M3", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("M4", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("P", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("Q1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("Q2", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("S1", 17);
INSERT INTO ConsumoEdificio(NomeEdificio, ConsumoAttuale)
VALUES("S2", 17);


-- INSERT INTO PER TABELLA CONSUMO

-- Crea una tabella temporanea con una colonna di date
CREATE TEMPORARY TABLE DateSeries (DateValue DATE);

-- Genera una serie di date dal 2023-01-12 al 2024-01-12
INSERT INTO DateSeries (DateValue)
SELECT DATE_ADD('2021-01-01', INTERVAL (n-1) DAY) AS DateValue
FROM
    (
        SELECT ROW_NUMBER() OVER () AS n
        FROM information_schema.columns
    ) AS Numbers
WHERE DATE_ADD('2021-01-01', INTERVAL (n-1) DAY) <= '2024-02-12';

-- Utilizza la serie di date per eseguire gli statement INSERT INTO
INSERT INTO ArchivioConsumo(DataConsumo, ConsumoGiornaliero, IdEdificio)
SELECT
    DateSeries.DateValue,
    ROUND(RAND() * (700 - 50) + 50) AS ConsumoGiornaliero,
    Numbers.n AS IdEdificio
FROM
    DateSeries,
    (
        SELECT ROW_NUMBER() OVER () AS n
        FROM information_schema.columns
    ) AS Numbers
WHERE
    Numbers.n BETWEEN 1 AND 39;

-- Elimina la tabella temporanea
DROP TEMPORARY TABLE IF EXISTS DateSeries;


INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 01);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 02);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 03);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 04);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 05);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 06);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 07);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 08);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 09);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 10);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 11);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 12);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 13);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 14);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 15);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 16);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 17);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 18);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 19);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 20);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 21);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 22);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 23);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 24);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 25);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 26);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 27);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 28);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 29);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 30);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 31);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 32);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 33);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 34);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 35);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 36);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 37);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 38);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(01, 39);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 01);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 02);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 03);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 04);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 05);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 06);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 07);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 08);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 09);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 10);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 11);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 12);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 13);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 14);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 15);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 16);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 17);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 18);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 19);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 20);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 21);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 22);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 23);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 24);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 25);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 26);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 27);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 28);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 29);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 30);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 31);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 32);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 33);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 34);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 35);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 36);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 37);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 38);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(02, 39);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 01);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 02);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 03);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 04);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 05);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 06);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 07);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 08);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 09);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 10);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 11);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 12);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 13);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 14);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 15);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 16);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 17);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 18);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 19);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 20);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 21);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 22);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 23);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 24);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 25);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 26);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 27);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 28);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 29);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 30);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 31);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 32);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 33);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 34);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 35);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 36);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 37);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 38);
INSERT INTO Utilizza(IdBatteria, IdEdificio)
VALUES(03, 39);

INSERT INTO TipoSorgente(Tipo)
VALUES("Pannello Fotovoltaico");
INSERT INTO TipoSorgente(Tipo)
VALUES("Servizio Elettrico Nazionale");

INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Servizio Elettrico Nazionale", "2000-01-01", 0, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 10, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 5, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 25, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 40, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 2, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 80, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 19, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 54, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 9, 1, 1);
INSERT INTO Sorgente(Tipologia, DataInstallazione, ProduzioneAttuale, FlagStatoSorgente, FlagAttivazioneSorgente)
VALUES("Pannello Fotovoltaico", "2023-01-01", 80, 1, 1);

-- Crea una tabella temporanea con una colonna di date
CREATE TEMPORARY TABLE DateSeries (DateValue DATE);

-- Genera una serie di date dal 2023-01-12 al 2024-01-12
INSERT INTO DateSeries (DateValue)
SELECT DATE_ADD('2021-01-01', INTERVAL (n-1) DAY) AS DateValue
FROM
    (
        SELECT ROW_NUMBER() OVER () AS n
        FROM information_schema.columns
    ) AS Numbers
WHERE DATE_ADD('2021-01-01', INTERVAL (n-1) DAY) <= '2024-02-12';

-- Utilizza la serie di date per eseguire gli statement INSERT INTO
INSERT INTO ArchivioProduzione(DataProduzione, ProduzioneGiornaliera, IdSorgente)
SELECT
    DateSeries.DateValue,
    ROUND(RAND() * (2000 - 0) + 0) AS ProduzioneGiornaliera,
    Numbers.n AS IdSorgente
FROM
    DateSeries
        JOIN (
        SELECT ROW_NUMBER() OVER () AS n
        FROM information_schema.columns
    ) AS Numbers ON Numbers.n BETWEEN 2 AND 11;

-- Elimina la tabella temporanea
DROP TEMPORARY TABLE IF EXISTS DateSeries;

INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 01);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 02);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 03);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 04);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 05);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 06);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 07);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 08);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 09);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 10);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(01, 11);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 01);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 02);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 03);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 04);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 05);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 06);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 07);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 08);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 09);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 10);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(02, 11);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 01);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 02);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 03);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 04);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 05);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 06);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 07);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 08);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 09);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 10);
INSERT INTO Caricare(IdBatteria, IdSorgente)
VALUES(03, 11);


INSERT INTO CondizioneMeteo(Condizione)
VALUES("Nuvoloso");
INSERT INTO CondizioneMeteo(Condizione)
VALUES("Piovoso");
INSERT INTO CondizioneMeteo(Condizione)
VALUES("Soleggiato");
INSERT INTO CondizioneMeteo(Condizione)
VALUES("Nevoso");
INSERT INTO CondizioneMeteo(Condizione)
VALUES("Ventilato");
INSERT INTO CondizioneMeteo(Condizione)
VALUES("Sereno");

INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-13", "00:00:00", 10, 2, "Sereno");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-13", "06:00:00", 20, 2, "Soleggiato");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-13", "12:00:00", 10, 2, "Soleggiato");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-13", "18:00:00", 25, 10, "Ventilato");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-14", "00:00:00", 10, 2, "Sereno");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-14", "06:00:00", 5, 90, "Piovoso");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-14", "12:00:00", 10, 80, "Piovoso");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-14", "18:00:00", 15, 15, "Nuvoloso");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-15", "00:00:00", 20, 90, "Nevoso");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-15", "06:00:00", 5, 2, "Soleggiato");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-15", "12:00:00", 5, 2, "Soleggiato");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-15", "18:00:00", 5, 2, "Sereno");
INSERT INTO Meteo(DataRilevazione, OraRilevazione, VelocitaVento, ProbabilitaPioggia, CondizioniMetereologiche)
VALUES("2024-01-16", "00:00:00", 10, 10, "Sereno");

INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 02);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 03);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 04);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 05);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 06);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 07);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 08);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 09);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 10);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (01, 11);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (06, 01);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 02);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 03);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 04);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 05);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 06);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 07);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 08);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 09);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 10);
INSERT INTO Influenzare(IdMeteo, IdSorgente)
VALUES (10, 11);

INSERT INTO ParametriIA(Piano, FlagAttivazioneParametro, IdAmministratore)
VALUES ("Salvaguardia Ambientale", 0, 01);
INSERT INTO ParametriIA(Piano, FlagAttivazioneParametro, IdAmministratore)
VALUES ("Efficienza Economica", 0, 01);
INSERT INTO ParametriIA(Piano, FlagAttivazioneParametro, IdAmministratore)
VALUES ("Personalizzato", 1, 02);

INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (01, "Servizio Elettrico Nazionale", 0, 30, 2);
INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (01, "Pannello fotovoltaico", 1, 100, 1);
INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (02, "Servizio Elettrico Nazionale", 0, 50, 2);
INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (02, "Pannello fotovoltaico", 1, 50, 1);
INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (03, "Servizio Elettrico Nazionale", 1, 80, 1);
INSERT INTO Interagisce(IdParametro, TipoSorgente, FlagPreferenzaSorgente, PercentualeUtilizzoSorgente, PrioritaSorgente)
VALUES (03, "Pannello fotovoltaico", 0, 90, 2);