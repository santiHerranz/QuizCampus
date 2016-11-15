
DROP TABLE if EXISTS usuari;
CREATE TABLE usuari
(
  usuariId int NOT NULL auto_increment PRIMARY KEY , --
  username VARCHAR(45) NOT NULL UNIQUE ,
  password VARCHAR(70) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  email VARCHAR (100) NULL,
  data_creacio DATE DEFAULT (sysdate)
);

DROP TABLE if EXISTS usuari_roles;
CREATE TABLE usuari_roles (
  user_role_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  usuariId int NOT NULL,
  role varchar(45) NOT NULL,
  UNIQUE KEY uni_username_role (role,usuariId),
  CONSTRAINT fk_usuariId FOREIGN KEY (usuariId) REFERENCES usuari (usuariId) ON DELETE CASCADE
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
  ordre int DEFAULT(0),  -- Ampliació: canviar l'ordre de les preguntes de l'enquesta
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




