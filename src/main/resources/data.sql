--TRUNCATE TABLE usuari;

-- 1234Az -> $2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W

INSERT INTO usuari (username, password) VALUES ('admin', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (1, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('demo', '$2a$10$3mq3AIslmDIc0yt0VJAmJOKxm6QR9enX0WhvjdwAJpz0A6xMC3FkS');
INSERT INTO usuari_roles (usuariId, role) VALUES (2, 'ROLE_USER');

INSERT INTO usuari (username, password) VALUES ('sherranzm', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (3, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (3, 'ROLE_USER');

INSERT INTO usuari (username,password) VALUES ('iargemi', '$2a$10$MnO4gDFJowx2ccvC1Knx5O0J/nqxWJXN/UXb6eMRYTHWnilmzgk7W');
INSERT INTO usuari_roles (usuariId, role) VALUES (4, 'ROLE_ADMIN');
INSERT INTO usuari_roles (usuariId, role) VALUES (4, 'ROLE_USER');


INSERT INTO enquesta (titol, data_creacio) VALUES ('Compra Online', {ts '2012-09-17 18:47:52.69'});
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,1,'Atencio al client', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,2,'Discrecio', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,3,'Producte', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (1,4,'Rapidessa d''entrega', 1, 5);


INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (2,1,5);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (2,2,3);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (2,3,5);
INSERT INTO resposta(usuariId, preguntaId,valor) VALUES (2,4,4);


INSERT INTO enquesta (titol, data_creacio) VALUES ('Valora el professorat', {ts '2012-11-26 00:00:00.00'});
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,1,'Durant el curs han estat clars els objectius, el programa de l''assignatura i els criteris d''avaluacio.', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,2,'L''assignatura globalment ha estat util per la meva formacio.', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,3,'El professor/a es mostra accessible (e-mails, hora de visita,etc.)', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,4,'El professor/a ha complert adequadament les seves obligacions docents (compliment del pla docent, puntualitat,etc.)', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,5,'El professor/a ens ha proporcionat suficients activitats per fer fora de l''aula.', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,6,'El professor/a ha compaginat adequadament la teoria amb la practica.', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,7,'S''ha treballat de forma regular a l''Aula virtual', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,8,'El professor/a ha aconseguit que m''interessi l''assignatura.', 1, 5);
INSERT INTO pregunta (enquestaId, ordre, enunciat, minim, maxim) VALUES (2,9,'Estic satisfet/a amb la docencia rebuda.', 1, 5);




