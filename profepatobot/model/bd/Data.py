import MySQLdb
from model.bd.Alumno import Alumno
from model.bd.Tag import Tag

class Data:

    def conectar(self):
        self.db = MySQLdb.connect(
            host="localhost",
            user="root",
            passwd="123456",
            db="profe_pato_bot"
        )
        self.cur = self.db.cursor()

    def desconectar(self):
        self.db.commit()
        self.db.close()

    def get_alumno(self, id):
        self.conectar()

        self.cur.execute("SELECT * FROM alumno WHERE id = "+str(id))

        alumno = None

        for row in self.cur.fetchall():
            alumno = Alumno(row[0], row[1])

        self.desconectar()

        return alumno

    def crear_alumno(self, alumno):
        self.conectar()

        insert = "INSERT INTO alumno VALUES('"+str(alumno.id)+"','"+str(alumno.nombre)+"', NOW())"
        self.cur.execute(insert)
        print(insert)

        self.desconectar()

    # tags - Lista de objetos Tag
    def crear_link(self, link, tags):
        self.conectar()

        insert = "INSERT INTO link VALUES(null,'"+link+"')"
        print(insert)
        self.cur.execute(insert)

        self.desconectar()

        id_link = self.get_last_id_link()

        self.conectar()

        for t in tags:
            insert = "INSERT INTO link_tag VALUES(NULL, '"+str(id_link)+"','"+str(t.id)+"')"
            self.cur.execute(insert)
            print(insert)

        self.desconectar()

    def get_last_id_link(self):
        self.conectar()

        select = "SELECT MAX(id) FROM link"

        self.cur.execute(select)

        id_link = -1
        for row in self.cur.fetchall():
            id_link = row[0]

        self.desconectar()
        return id_link

    def reg_mensaje(self, mensaje):
        self.conectar()

        insert = "INSERT INTO mensaje VALUES(NULL, '"+str(mensaje.id_alumno)+"', '"+str(mensaje.mensaje)+"', NOW())";
        self.cur.execute(insert)

        self.desconectar()

    def get_links(self, tag):
        self.conectar()

        select = "SELECT l.id AS 'ID link', l.valor AS 'Link',t.id AS 'ID tag', t.valor AS 'Tag' FROM link_tag lt INNER JOIN link l ON l.id = lt.link INNER JOIN tag t ON t.id = lt.tag WHERE t.valor LIKE '%"+tag+"%' GROUP BY l.id;"

        self.cur.execute(select)

        links = list()

        for row in self.cur.fetchall():
            links.append(row[1])

        self.desconectar()

        return links


    def get_tags(self):
        self.conectar()

        select = "SELECT * FROM tag"
        self.cur.execute(select)

        tags = list()
        for row in self.cur.fetchall():
            t = Tag(row[0], row[1])
            tags.append(t)

        self.desconectar()

        return tags