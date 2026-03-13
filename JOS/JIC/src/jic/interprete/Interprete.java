package jic.interprete;

import jic.argumentos.Argumento;
import jic.comandos.Comando;
import jic.comandos.Comandos;

public class Interprete {

    public Interprete() {
        Comandos.load();
    }

    /**
     * Éste es el método principal del Interprete. Es el que interpreta lo que
     * el usuario escribe
     *
     * @param lineaComando Es la linea completa que el usuario ingresa
     * incluyendo los argumentos
     */
    public void ejecutar(String lineaComando) {
        try {
            // Si existe dudas de porque llamé a este método, ver la 
            // Documentación del mismo.
            String[] argumentos = Argumento.getArgumentos(lineaComando);

            // El argumento 0 es el nombre del comando
            Comando comandoActual = Comandos.getComando(argumentos[0]);

            // Si el comando no existe
            if (comandoActual == null) {
                System.out.println("\"" + argumentos[0] + "\" no se reconoce como un comando interno del sistema.");
                System.out.println("Intente con " + Comandos.ayuda.getNombres());
            } else {
                comandoActual.ejecutar(argumentos);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error de escritura de comando.");
            System.out.println("Intente con " + Comandos.ayuda.getNombres());
        }

    }

    
}
