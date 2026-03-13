package cl.chilean.ts;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChileanBot extends TelegramLongPollingBot {

    public ChileanBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return "ChileanTsBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();

            String response = switch (text) {
                case "/ping" -> "🏓 Pong desde Spring Boot!";
                case "/help" -> "📖 Comandos disponibles: /ping, /help";
                default -> "🤖 No entiendo ese comando.";
            };

            if (text.equals("/ls")) {
                String output = listDirectory("share");
                sendText(chatId, output);
                return;
            }

            if (text.startsWith("/download ")) {
                String filename = text.substring(10).trim();
                sendFile(chatId, filename);
                return;
            }

            sendText(chatId, response);
        }
    }

    private String listDirectory(String path) {
        File dir = new File(path);

        if (!dir.exists() || !dir.isDirectory()) {
            return "❌ Directorio no válido: " + path;
        }

        String[] files = dir.list();
        if (files == null || files.length == 0) {
            return "📂 El directorio está vacío.";
        }

        return "📁 Contenido de " + path + ":\n" + String.join("\n", files);
    }

    private void sendText(String chatId, String message) {
        try {
            execute(new SendMessage(chatId, message));
        } catch (TelegramApiException e) {
            log.error("Error al enviar mensaje: {}", e.getMessage(), e);
        }
    }

    private void sendFile(String chatId, String filename) {
        File file = new File("share", filename); // ruta relativa al directorio 'share'

        if (!file.exists() || !file.isFile()) {
            sendText(chatId, "❌ Archivo no encontrado: " + filename);
            return;
        }

        SendDocument sendDocument = new SendDocument();

        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new org.telegram.telegrambots.meta.api.objects.InputFile(file));

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("Error al enviar el archivo: {}", e.getMessage(), e);
            sendText(chatId, "❌ Error al enviar el archivo.");
        }
    }

}
