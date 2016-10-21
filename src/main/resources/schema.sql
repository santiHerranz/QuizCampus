
DROP TABLE if EXISTS usuari;
CREATE TABLE usuari
(
  usuariId int auto_increment PRIMARY KEY , --
  email VARCHAR (100) NOT NULL UNIQUE ,
  contrasenya VARCHAR (100) NOT NULL,
  admin BOOLEAN NOT NULL DEFAULT (0)
  , data_creacio DATE DEFAULT (sysdate)
);

DROP TABLE if EXISTS enquesta;
CREATE TABLE enquesta
(
  enquestaId int auto_increment PRIMARY KEY, --
  titol VARCHAR (200) NOT NULL UNIQUE
  , data_creacio DATE DEFAULT (sysdate)
);

DROP TABLE if EXISTS pregunta;
CREATE TABLE pregunta
(
  preguntaId int auto_increment PRIMARY KEY, --
  enquestaId int NOT NULL,
  enunciat VARCHAR (500) NOT NULL,
  --ordre int,  -- Ampliació: canviar l'ordre de les preguntes de l'enquesta
  minim int,
  maxim int
  , data_creacio DATE DEFAULT (sysdate)
  , FOREIGN KEY (enquestaId) REFERENCES enquesta(enquestaId) ON DELETE CASCADE
);


DROP TABLE if EXISTS resposta;
CREATE TABLE resposta
(
  respostaId int auto_increment PRIMARY KEY, --
  usuariId int NOT NULL,
  preguntaId int NOT NULL,
  valor int NOT NULL
  , data_creacio DATE DEFAULT (sysdate)
  , FOREIGN KEY (usuariId) REFERENCES usuari(usuariId) ON DELETE CASCADE
);
/*
*/




