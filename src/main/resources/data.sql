--TRUNCATE TABLE usuari;

-- 1234Az -> $2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W

INSERT INTO usuari (username, password) VALUES ('admin', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('normal', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (2, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('sherranzm', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (3, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (3, 'ROLE_USER');

INSERT INTO usuari (username,password) VALUES ('iargemi', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (4, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (4, 'ROLE_USER');


INSERT INTO enquesta (titol, data_creacio) VALUES ('Compra Online', {ts '2012-09-17 18:47:52.69'});
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,1,'Atenció al client', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,2,'Discreció', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,3,'Producte', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,4,'Rapidessa d''entrega', 1, 5);


INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (1,1,5);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (1,2,5);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (1,3,5);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (1,4,4);