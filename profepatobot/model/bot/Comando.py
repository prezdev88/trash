class Comando:
    # Constructor
    def __init__(self, nombre, mensajes, accion=None):
        self.nombre = nombre
        self.mensajes = mensajes
        self.accion = accion

    def get_mensaje_random(self):
        largo = len(self.mensajes)

        from random import randint
        return self.mensajes[randint(0, largo-1)]

    # toString();
    def __str__(self):
        return "Comando:{nombre="+self.nombre+", descripcion="+self.descripcion+", mensaje="+self.mensaje+"}"
