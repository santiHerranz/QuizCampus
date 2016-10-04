
DROP TABLE if EXISTS usuari;
CREATE TABLE usuari
(
  usuariId int auto_increment PRIMARY KEY,
  email VARCHAR (100),
  contrasenya VARCHAR (100) NOT NULL
);


DROP TABLE if EXISTS resposta;
CREATE TABLE resposta
(
  respostaId int auto_increment PRIMARY KEY,
  usuariId int NOT NULL,
  valor int NOT NULL
  --, CONSTRAINT fk_1 FOREIGN KEY (usuariId) REFERENCES usuari(usuariId)
);



