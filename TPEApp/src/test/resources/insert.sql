INSERT INTO FILM VALUES (1,'El hombre más buscado','Anton Corbijin','2014-09-01','2014-09-01','Suspenso','A Chechen Muslim illegally immigrates to Hamburg, where he gets caught in the international war on terror.',73,0,0);
INSERT INTO FILM VALUES (2,'Lo que el viento se llevó','El director','2014-09-01','1970-09-01','Drama','Sopla mucho viento, y se lleva todo....',90,6,2);
INSERT INTO FILM VALUES (3,'Casablanca','George W. Bush','2014-09-01','1980-09-01','Documental','Película sobre una casa blanca en Estados Unidos.',90,0,0);
INSERT INTO FILM VALUES (4,'Forest Gump','El forest','2014-09-01','1981-09-01','Acción','COOOOORREEEE FOREST!!!!',120,0,0);
INSERT INTO FILM VALUES (5,'Ghost, La sombra del amor','El jarrón de barro','2014-09-01','1985-09-01','Suspenso','Se murió y se hizo fantasma.',90,0,0);
INSERT INTO FILM VALUES (6,'Sexto sentido','El de la llamada','2014-09-01','1985-09-01','Suspenso','La continuación de quinto sentido.',80,10,4);
INSERT INTO FILM VALUES (7,'La bala que dobló en la esquina','El de la llamada','2014-09-01','1980-09-01','Terror','Venía derecho y al final dobló.... En la esquina...',90,0,0);
INSERT INTO FILM VALUES (8,'Star Wars: La venganza del jeti','Jorge Lucas','2014-09-01','1980-09-01','Suspenso','El Jeti de las nieves decide vengarse de Anakin.',45,3,1);
INSERT INTO FILM VALUES (9,'Inception','Perinola','2014-09-01','2010-09-01','Suspenso','Vienen jugando a la perinola, hasta que se pudre todo.',90,0,0);
INSERT INTO FILM VALUES (10,'Próximamente','agregoire','2014-09-01','2020-09-01','Documental','La historia de unos chicos y su Aplicación Web.',90,0,0);
INSERT INTO FILM VALUES ('Gladiador', 'El loco bielsa', '2014-09-16', '2014-09-20', 'Acción', 'Gladiator (Gladiador en Hispanoamérica y Gladiator (El gladiador) en España) es una película épica del género péplum estrenada en el año 2000. Dirigida por Ridley Scott, sus papeles principales son interpretados por Russell Crowe, Joaquin Phoenix, Connie Nielsen, Ralf Möller, Oliver Reed, Djimon Hounsou, Derek Jacobi, John Shrapnel y Richard Harris. Crowe interpreta a Máximo Décimo Meridio.', 120, 0, 0)
INSERT INTO FILM VALUES ('Volver al futuro', 'Doc Brow', '2014-09-16', '2014-09-14', 'Documental', 'La historia transcurre en el año 1985 y cuenta las aventuras del joven de 17 años Marty McFly, que vive con sus padres y sus hermanos en la ficticia ciudad de Hill Valley, California. Marty tiene por su mejor amigo al científico "Doc" Emmett Brown, el cual un día le pide a Marty que le ayude con su último invento, que resulta ser una máquina del tiempo fabricada a partir de un automóvil DeLorean.', 124, 0, 0)

ALTER SEQUENCE film_id_seq RESTART WITH 13;

INSERT INTO GAJAMDBUSER VALUES (1,'asciglia@itba.edu.ar','123456', 'Agustin','Scigliano','1990-11-14','Quién es tu profesor favorito?','Horacio Rinaldi',FALSE);
INSERT INTO GAJAMDBUSER VALUES (2,'amedvede@itba.edu.ar','123456', 'Alexis','Medvedeff','1989-11-14','te gusta el SABF?','ME ENCANTA',FALSE);
INSERT INTO GAJAMDBUSER VALUES (3,'gdelgiud@itba.edu.ar','123456', 'Gustavo','Del Giudice','1989-12-13','Que cosa no usan los vikingos?','abrigo',TRUE);
INSERT INTO GAJAMDBUSER VALUES (4,'jugutier@itba.edu.ar','123456', 'Julian','Gutierrez','1991-03-31','A quién le preguntas las dudas del TPE?','Andres Gregoire',FALSE);

ALTER SEQUENCE gajamdbuser_id_seq RESTART WITH 5;

INSERT INTO COMMENT VALUES (1,2,2,'2014-09-01 09:00:00', 'Este es un comentario de prueba, porque me encanta comentar',3);
INSERT INTO COMMENT VALUES (2,2,1,'2014-09-01 09:00:00', 'Este es un comentario de prueba, porque a ale le encanta comentar',3);
INSERT INTO COMMENT VALUES (3,8,3,'2014-09-01 09:00:00', 'Esta pelicula es tan mala como mi código',3);
INSERT INTO COMMENT VALUES (4,6,2,'2014-09-01 09:00:00', 'No me gusto mucho. Al final nos juntamos el viernes?',1);
INSERT INTO COMMENT VALUES (5,6,4,'2014-09-01 09:01:00', 'A mi me pareció buena. Dale, si no tenes nada del SABF sí.',4);
INSERT INTO COMMENT VALUES (6,6,3,'2014-09-01 09:02:00', 'Yo no me voy a juntar, ya hice todas mis tareas.',0);
INSERT INTO COMMENT VALUES (7,6,1,'2014-09-01 09:03:00', 'Viernes pre con minas, voy a estar roto.',5);

ALTER SEQUENCE comment_id_seq RESTART WITH 8;
