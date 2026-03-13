package jic.main;

import java.util.Date;
import java.util.Scanner;
import jic.archivos.Archivo;
import jic.comandos.Comando;
import jic.interprete.Interprete;

public class MainSystem {
    private static Archivo raiz;
    public static Archivo archivoActual;
    public static Archivo archivoPadre;
    private final String VERSION = "v0.1";
    public static final String NOMBRE  = "JIC";
    public static String prompt;
    
    private final Scanner scan;
    private final Interprete interprete;
    private String lineaComando;
    private Comando comandoActual;
    
    
    public MainSystem(){
        this.interprete = new Interprete();
        this.scan = new Scanner(System.in);
        
        init();
    }
    
    public void run(){
        System.out.println(NOMBRE+" - "+VERSION+" - "+new Date());
        
        while(true){
            System.out.print(prompt);
            lineaComando = scan.nextLine();
            interprete.ejecutar(lineaComando);
        }
    }

    private void init() {
        raiz = new Archivo("/", false);
        archivoActual = raiz;
        archivoPadre = null;
        prompt  = MainSystem.NOMBRE+":$ ";
    }
}
