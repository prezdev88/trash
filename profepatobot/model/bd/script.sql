CREATE DATABASE profe_pato_bot;

USE profe_pato_bot;

CREATE TABLE alumno(
  id      INT,
  nombre  VARCHAR(200),
  fecha   DATETIME,
  PRIMARY KEY (id)
);

CREATE TABLE mensaje (
  id      INT AUTO_INCREMENT,
  alumno  INT,
  mensaje VARCHAR(2000),
  fecha   DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (alumno) REFERENCES alumno(id)
);

CREATE TABLE tag (
  id      INT AUTO_INCREMENT,
  valor   VARCHAR(200),
  PRIMARY KEY (id)
);

INSERT INTO tag VALUES(NULL, 'python');
INSERT INTO tag VALUES(NULL, 'java');
INSERT INTO tag VALUES(NULL, 'base de datos');
INSERT INTO tag VALUES(NULL, '.net');
INSERT INTO tag VALUES(NULL, 'java web');
INSERT INTO tag VALUES(NULL, 'poo');
INSERT INTO tag VALUES(NULL, 'asp');
INSERT INTO tag VALUES(NULL, 'c#');
INSERT INTO tag VALUES(NULL, 'sesiones');
INSERT INTO tag VALUES(NULL, 'mssql');
INSERT INTO tag VALUES(NULL, 'mysql');
INSERT INTO tag VALUES(NULL, 'ajax');
INSERT INTO tag VALUES(NULL, 'crud');

CREATE TABLE link (
  id      INT AUTO_INCREMENT,
  valor   VARCHAR(2000),
  PRIMARY KEY(id)
);

INSERT INTO link VALUES (null, 'https://www.youtube.com/watch?v=_B0niULnc5s'); /*POO*/
INSERT INTO link VALUES (null, 'https://www.youtube.com/watch?v=ChXXB2jPwv8'); /*ASP y MS SQL y C#*/
INSERT INTO link VALUES (null, 'https://www.youtube.com/watch?v=LG_ncb4VzSQ'); /*Java web y mysql*/
INSERT INTO link VALUES (null, 'https://www.youtube.com/watch?v=qO510Nnp4JU'); /*c#, mssql, crud*/

CREATE TABLE link_tag (
  id      INT AUTO_INCREMENT,
  link    INT,
  tag     INT,
  PRIMARY KEY(id),
  FOREIGN KEY(link) REFERENCES link(id),
  FOREIGN KEY(tag)  REFERENCES tag(id)
);

INSERT INTO link_tag VALUES (NULL, '1','6');
INSERT INTO link_tag VALUES (NULL, '2','7');
INSERT INTO link_tag VALUES (NULL, '2','10');
INSERT INTO link_tag VALUES (NULL, '2','8');
INSERT INTO link_tag VALUES (NULL, '3','5');
INSERT INTO link_tag VALUES (NULL, '3','11');
INSERT INTO link_tag VALUES (NULL, '3','12');
INSERT INTO link_tag VALUES (NULL, '4','8');
INSERT INTO link_tag VALUES (NULL, '4','10');
INSERT INTO link_tag VALUES (NULL, '4','13');

/*Videos y sus tags*/
SELECT
  l.id AS 'ID link',
  l.valor AS 'Link',
  t.id AS 'ID tag',
  t.valor AS 'Tag'
FROM
  link_tag lt
  INNER JOIN link l ON l.id = lt.link
  INNER JOIN tag t ON t.id = lt.tag;


/*Videos de un tag determinado*/
SELECT
  l.id AS 'ID link',
  l.valor AS 'Link',
  t.id AS 'ID tag',
  t.valor AS 'Tag'
FROM
  link_tag lt
  INNER JOIN link l ON l.id = lt.link
  INNER JOIN tag t ON t.id = lt.tag
WHERE
  t.valor LIKE '%mssql%' OR
  t.valor LIKE '%java web%'
  GROUP BY l.id;

/*Listado de todos los mensaje*/
SELECT
  m.id,
  a.nombre AS 'Alumno',
  m.mensaje,
  m.fecha
FROM
  alumno a
  INNER JOIN mensaje m ON a.id = m.alumno;

SELECT * FROM tag;
SELECT * FROM mensaje;
SELECT * FROM link;
SELECT * FROM link_tag;
SELECT * FROM alumno;

DROP DATABASE profe_pato_bot;