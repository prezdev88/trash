# http://effbot.org/tkinterbook/grid.htm
# https://www.tutorialspoint.com/python/python_gui_programming.htm

# Eventos con bind()
# http://effbot.org/tkinterbook/tkinter-events-and-bindings.htm

# ComboBox
# http://recursospython.com/guias-y-manuales/lista-desplegable-combobox-en-tkinter/

from tkinter import *
from tkinter import ttk
from model.bd.Data import Data

class Application(Frame):

    """
    El constructor no cambia (por ahora)
    """
    def __init__(self, master=None):
        self.tags = None
        self.data = Data()
        self.cont = 1

        self.list_cbos = list()
        self.list_botones = list()

        super().__init__(master)
        self.pack()
        self.init_components()
        self.evt_btn_generar(None)

    def init_components(self):
        self.lbl1 = Label(self)
        self.lbl1["text"] = "URL:"
        self.lbl1.grid(row=0, column=0)
        # padx = relleno hacia afuera en x

        # Entry = TextBox
        self.txt_link = Entry(self)
        self.txt_link.grid(
            row=0,
            column=1,
            columnspan=3,
            ipadx = 100
        )
        self.txt_link.bind("<Return>", self.evt_txt_link) # Evento enter (Alucinante!)

        self.lbl2 = Label(self)
        self.lbl2["text"] = "Tags:"
        self.lbl2.grid(row=1, column=0)

        self.btn_generar = Button(self)
        self.btn_generar["text"] = "+"
        self.btn_generar.grid(row=1, column=1)
        self.btn_generar.bind("<Button-1>", self.evt_btn_generar)

        self.btn_guardar = Button(self)
        self.btn_guardar["text"] = "Registrar"
        self.btn_guardar.grid(row=2, column=1, columnspan=4)
        self.btn_guardar.bind("<Button-1>", self.evt_btn_guardar)

    def evt_btn_guardar(self, evt):
        tags = list()

        for c in self.list_cbos:
            selected_index = c.current()
            tag = self.tags[selected_index]
            tags.append(tag)

        link = self.txt_link.get()

        self.data.crear_link(link, tags)

    def evt_btn_generar(self, evt):
        cbo = ttk.Combobox(self, state="readonly")
        cbo.extra = "cbo_"+str(self.cont)

        if self.tags is None:
            self.tags = self.data.get_tags()

        cbo["values"] = self.tags

        cbo.grid(row=self.cont, column=2)

        btn_eli = Button(self)
        btn_eli.extra = str(self.cont)
        btn_eli["text"] = "Eliminar tag"
        btn_eli.grid(row=self.cont, column=3)
        self.cont = self.cont + 1
        btn_eli.bind("<Button-1>", self.evt_btn_eli)
        self.list_botones.append(btn_eli)

        #cbo.current(0)

        self.list_cbos.append(cbo)

        self.btn_guardar.grid(row=self.cont+1, column=1, ipadx=70)

    def evt_btn_eli(self, evt):
        #evt.widget.cget("text") --> Obtengo el texto del widget
        # print("Nombre cbo = cbo_"+evt.widget.extra)
        # print("Nombre boton: "+evt.widget.extra)

        # Elimino el combo
        for c in self.list_cbos:
            if c.extra == "cbo_"+evt.widget.extra:
                c.grid_remove()
                self.list_cbos.remove(c)
                break

        # Elimino los botones
        for b in self.list_botones:
            if b.extra == evt.widget.extra:
                b.grid_remove()
                self.list_botones.remove(b)
                break

        self.cont = self.cont - 1

        i = 1
        for c in self.list_cbos:
            c.grid(row=i, column=2)
            i = i + 1

        i = 1
        for b in self.list_botones:
            b.grid(row=i, column=3)
            i = i + 1

    def evt_cbo(self, evt):
        print(evt)


    def evt_txt_link(self, evt):
        print(evt)
        print(self.txt_link.get())

root = Tk()
app = Application(master=root)
app.mainloop()