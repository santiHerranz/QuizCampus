--TRUNCATE TABLE usuari;


INSERT INTO usuari (email,contrasenya, admin) VALUES ('sherranzm@edu.tecnocampus.cat', '12345', true);
INSERT INTO usuari (email,contrasenya) VALUES ('iargemi@edu.tecnocampus.cat', '12345');
INSERT INTO usuari (email,contrasenya) VALUES ('1111111@edu.tecnocampus.cat', '12345');
INSERT INTO usuari (email,contrasenya) VALUES ('2222222@edu.tecnocampus.cat', '12345');
INSERT INTO usuari (email,contrasenya) VALUES ('3333333@edu.tecnocampus.cat', '12345');
INSERT INTO usuari (email,contrasenya) VALUES ('4444444@edu.tecnocampus.cat', '12345');

--TRUNCATE TABLE enquesta;
--TRUNCATE TABLE pregunta;
--TRUNCATE TABLE resposta;


INSERT INTO enquesta (titol) VALUES ('General');
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora les instalacions', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora els laboratoris', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora la biblioteca', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora la cafeteria', 1, 10);


INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (1,1,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (1,2,6.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (1,3,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (1,4,6.0);


INSERT INTO enquesta (titol) VALUES ('Professors en general');

INSERT INTO enquesta (titol) VALUES ('Laboratoris Informàtica');

INSERT INTO enquesta (titol) VALUES ('Aules');

INSERT INTO enquesta (titol) VALUES ('Serveis');

/*



INSERT INTO enquesta (enquestaId, titol) VALUES (2,'Primer curs');
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (5, 2,'Valora els profesors', 1, 10);
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (6, 2,'Valora els horaris', 1, 10);

INSERT INTO enquesta (enquestaId, titol) VALUES (3,'Segon curs');
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (7,3,'Valora els profesors', 1, 10);
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (8,3,'Valora els horaris', 1, 10);

INSERT INTO enquesta (enquestaId, titol) VALUES (4,'tercer curs');
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (9,4,'Valora els profesors', 1, 10);
INSERT INTO pregunta (preguntaId, enquestaId, enunciat) VALUES (9,5,'Valora els horaris', 1, 10);



*/
