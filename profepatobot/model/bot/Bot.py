import time

import telegram
from telegram.ext import *

from model.bot.Comandos import Comandos
from model.bd.Data      import Data
from model.bd.Alumno    import Alumno
from model.bd.Mensaje   import Mensaje

class Bot:
    @staticmethod
    def init():
        Bot.data = Data()
        Comandos.init()
        Bot.bot = telegram.Bot(token="406813267:AAEvEQwnHNvwYXNuI0C2v9Ub9n6s7iRx_qA")
        Bot.bot_updater = Updater(Bot.bot.token)
        Bot.dispatcher = Bot.bot_updater.dispatcher

        start_handler = CommandHandler("start", Bot.start)

        listener_handler = MessageHandler(Filters.text, Bot.listener)
        Bot.dispatcher.add_handler(start_handler)
        Bot.dispatcher.add_handler(listener_handler)

        Bot.bot_updater.start_polling()
        print("Bot iniciado!")
        Bot.bot_updater.idle()

    # Método que se llama cuando el usuario pone /start
    @staticmethod
    def start(bot, update, pass_chat_data=True):
        Bot.id = update.message.chat_id

        alumno = Bot.data.get_alumno(Bot.id)

        if alumno is None:
            info = bot.getChat(update.message.chat_id)

            if (info['last_name'] is None):
                alumno = Alumno(info['id'], info['first_name'])
            else:
                alumno = Alumno(info['id'], info['first_name'] + " " + info['last_name'])

            Bot.data.crear_alumno(alumno)

        Bot.enviarMensaje("Hola " + alumno.nombre + ". En que te puedo ayudar?")

    @staticmethod
    def listener(bot, update):
        try:
            mensaje = update.message.text
            Bot.id = update.message.chat_id
            print("ID: " + str(Bot.id) + " Mensaje:" + mensaje)

            alumno = Bot.data.get_alumno(Bot.id)

            links = Bot.data.get_links(mensaje)

            if len(links) != 0:
                for l in links:
                    Bot.enviarMensaje(l)
            else:
                # no se encontraron links

                c = Comandos.get(mensaje)

                if c is not None:
                    Bot.enviarMensaje(c.get_mensaje_random())
                else:
                    Bot.enviarMensaje("Soory, no encontré material. Intenta con estas búsquedas:")

                    string = ""
                    for t in Bot.data.get_tags():
                        string += t.valor+"\n"

                    Bot.enviarMensaje(string)

                men = Mensaje(alumno.id, mensaje)
                Bot.data.reg_mensaje(men)
        except BaseException as b:
            print(str(b))

    @staticmethod
    def enviarMensaje(mensaje):
        #"[" + time.strftime("%H:%M:%S") + "] " +
        Bot.bot.send_chat_action(
            chat_id=Bot.id,
            action=telegram.ChatAction.TYPING
        )
        Bot.bot.sendMessage(
            chat_id=Bot.id,
            text=mensaje
        )