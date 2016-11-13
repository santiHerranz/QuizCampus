--TRUNCATE TABLE usuari;

-- 1234567Ab -> $2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC

INSERT INTO usuari (username, password) VALUES ('admin', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('normal', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (2, 'ROLE_USER');

INSERT INTO usuari (username,password) VALUES ('33333', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (3, 'ROLE_USER');

INSERT INTO usuari (username,password) VALUES ('44444', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (4, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('sherranzm', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (5, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (5, 'ROLE_USER');


INSERT INTO usuari (username,password) VALUES ('iargemi', '$2a$10$eg0cwZu8qqxSVbb2x1xIk.ch3UN/Gn6LBip4WrVhMsxqDUcNzIcUC');
INSERT INTO usuari_roles (usuariId, role) VALUES (6, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (6, 'ROLE_USER');


--TRUNCATE TABLE enquesta;
--TRUNCATE TABLE pregunta;
--TRUNCATE TABLE resposta;


INSERT INTO enquesta (titol) VALUES ('General');
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora les instalacions', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora els laboratoris', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora la biblioteca', 1, 10);
INSERT INTO pregunta (enquestaId, enunciat, minim, maxim) VALUES (1,'Valora la cafeteria', 1, 10);


INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (3,1,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (3,1,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (3,2,6.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (3,3,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (3,4,6.0);

INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (4,1,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (4,1,7.0);
INSERT INTO resposta (usuariId, preguntaId, valor) VALUES (4,2,6.0);


INSERT INTO enquesta (titol, data_creacio) VALUES ('Professors en general', {ts '2012-09-17 18:47:52.69'} );

INSERT INTO enquesta (titol, data_creacio) VALUES ('Laboratoris Informàtica', {ts '2012-09-17 18:47:52.69'});

INSERT INTO enquesta (titol, data_creacio) VALUES ('Aules', {ts '2012-09-17 18:47:52.69'});

INSERT INTO enquesta (titol, data_creacio) VALUES ('Serveis', {ts '2012-09-17 18:47:52.69'});

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
