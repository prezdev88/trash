import time

from model.bot.Comando import Comando


class Comandos:
    @staticmethod
    def init():

        mensajes = list()

        mensajes.append("De nada! 👍")
        mensajes.append("OKAAA 👍")
        mensajes.append("No hay de que! 👍")
        mensajes.append("De nada, vuelve cuando quieras! 👍")

        Comandos.add(
            Comando(
                "gracias",
                mensajes
            )
        )

        mensajes = list()

        mensajes.append("Hola")
        mensajes.append("Wena wena")
        mensajes.append("Hola viej@! qué tal")
        mensajes.append("Wena viej@!")

        Comandos.add(
            Comando(
                "hola",
                mensajes
            )
        )

        Comandos.add(
            Comando(
                "ola",
                mensajes
            )
        )

        mensajes = list()

        mensajes.append("bien, acá y tú? qué necesitas?")
        mensajes.append("bien bien y tú? en qué te puedo ayudar?")
        mensajes.append("Acá programando y tú? cuéntame que necesitas")

        Comandos.add(
            Comando(
                "como esta",
                mensajes
            )
        )
        Comandos.add(
            Comando(
                "cómo está",
                mensajes
            )
        )

        mensajes = list()
        mensajes.append("m?")

        Comandos.add(
            Comando(
                "profe",
                mensajes
            )
        )

        mensajes = list()
        mensajes.append("oka!")
        mensajes.append("okaaaaa!")
        mensajes.append("OKAAAAAAA!")

        Comandos.add(
            Comando(
                "oka",
                mensajes
            )
        )

    @staticmethod
    def add(comando):
        try:
            Comandos.lista.append(comando)
        except:
            # si cae en esta excepcion es porque no esta creada
            Comandos.lista = list()
            Comandos.lista.append(comando)

    @staticmethod
    def get(nombreComando):
        for c in Comandos.lista:
            if(c.nombre in nombreComando.lower()):
                return c

        return None

    @staticmethod
    def print():
        str = "Lista de comandos disponibles\n\n"
        str += "/start - Muestra esta lista de comandos\n"
        for c in Comandos.lista:
            print(c)
            str += c.nombre + " - "+ c.descripcion + "\n"

        return str