
DROP TABLE if EXISTS usuari;
CREATE TABLE usuari
(
  usuariId int auto_increment PRIMARY KEY , --
  email VARCHAR (100),
  contrasenya VARCHAR (100) NOT NULL,
  admin BOOLEAN NOT NULL DEFAULT (0)
);

DROP TABLE if EXISTS enquesta;
CREATE TABLE enquesta
(
  enquestaId int auto_increment PRIMARY KEY, --
  titol VARCHAR (200) NOT NULL
);

DROP TABLE if EXISTS pregunta;
CREATE TABLE pregunta
(
  preguntaId int auto_increment PRIMARY KEY, --
  enquestaId int NOT NULL,
  enunciat VARCHAR (500) NOT NULL,
  minim int,
  maxim int
  --, CONSTRAINT fk_1 FOREIGN KEY (usuariId) REFERENCES usuari(usuariId)
);


DROP TABLE if EXISTS resposta;
CREATE TABLE resposta
(
  respostaId int auto_increment PRIMARY KEY, --
  usuariId int NOT NULL,
  preguntaId int NOT NULL,
  valor int NOT NULL
  --, CONSTRAINT fk_1 FOREIGN KEY (usuariId) REFERENCES usuari(usuariId)
);
/*
*/




