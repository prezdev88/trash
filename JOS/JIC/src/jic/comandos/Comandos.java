package jic.comandos;

import java.util.ArrayList;
import jic.archivos.Archivo;
import jic.main.MainSystem;

public class Comandos {

    private static ArrayList<Comando> comandos;
    public static Comando ayuda;

    static {
        Comandos.comandos = new ArrayList<>();
    }

    /*ACÁ CREAR TODOS LOS COMANDOS!*/
    public static void load() {
        ayuda = new Comando("help", "ayuda", "h")
                .setDescripcion("Muestra la ayuda del sistema")
                .setEjecucion((String[] argumentos) -> {
                    System.out.println("Lista de comandos disponibles:");

                    for (Comando c : comandos) {
                        System.out.println(c.getNombres());
                        System.out.println("    " + c.getDescripcion());
                    }
                });

        Comandos.comandos.add(ayuda);

        Comandos.comandos.add(new Comando("exit", "salir")
                .setDescripcion("Sale del sistema")
                .setEjecucion((String[] argumentos) -> {
                    System.exit(0);
                })
        );

        Comandos.comandos.add(new Comando("pwd")
                .setDescripcion("Ver la ruta actual")
                .setEjecucion((String[] argumentos) -> {
                    System.out.println(MainSystem.archivoActual.getNombre());
                })
        );

        Comandos.comandos.add(new Comando("prompt")
                .setDescripcion("Cambia el prompt actual del sistema")
                .setEjecucion((String[] argumentos) -> {
                    try {
                        MainSystem.prompt = argumentos[1];
                        System.out.println("El prompt se ha cambiado a " + argumentos[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        MainSystem.prompt = MainSystem.NOMBRE + ":$ ";
                    }
                })
        );

        Comandos.comandos.add(new Comando("mkdir")
                .setDescripcion("Crea carpetas")
                .setEjecucion((String[] args) -> {
                    for (int i = 1; i < args.length; i++) {
                        Archivo ar = new Archivo(args[i], false);
                        if (MainSystem.archivoActual.existeArchivoHijo(ar) == null) {
                            MainSystem.archivoActual.addHijo(ar);
                        } else {
                            System.out.println("\"" + ar.getNombre() + "\" ya existe");
                        }
                    }
                })
        );

        Comandos.comandos.add(new Comando("ls", "dir")
                .setDescripcion("Lista los archivos y carpetas")
                .setEjecucion((String[] args) -> {
                    System.out.println("Carpetas y archivos de: " + MainSystem.archivoActual.getNombre() + "\n");

                    for (Archivo ar : MainSystem.archivoActual.getHijos()) {
                        System.out.println((ar.isArchivo() ? "ARC" : "CAR") + "\t" + ar.getNombre() + "\t " + ar.size() + " bytes");
                    }
                })
        );

        Comandos.comandos.add(new Comando("cd")
                .setDescripcion("Entra / Sale de una carpeta")
                .setEjecucion((String[] args) -> {
                    try {
                        if (args[1].equals("..")) {
                            // volver atras
                            if(MainSystem.archivoPadre != null){
                                MainSystem.archivoActual = MainSystem.archivoPadre;
                            }
                        } else {
                            // etrar a carpeta
                            Archivo ar = new Archivo(args[1], false);
                            Archivo arcBuscar = MainSystem.archivoActual.existeArchivoHijo(ar);
                            if(arcBuscar != null){
                                MainSystem.archivoPadre = MainSystem.archivoActual;
                                MainSystem.archivoActual = arcBuscar;
                            }else{
                                System.out.println("No existe la carpeta \""+args[1]+"\"");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Intenta \"cd nombre_carpeta\"");
                    }
                })
        );
    }

    public static Comando getComando(String nombreComando) {
        for (Comando c : comandos) {
            if (c.isNombre(nombreComando)) {
                return c;
            }
        }
        return null;
    }
}
