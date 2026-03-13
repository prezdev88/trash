package jic.comandos;

import jic.interprete.Ejecutable;
import java.util.ArrayList;

public class Comando {
    private String[] nombres;
    private ArrayList<String> argumentos;
    private String descripcion;
    private Ejecutable ejecutable;

    public Comando() {
    }

    /**
     * Este constructor es para que los comandos tenga multinombres
     * @param nombres 
     */
    public Comando(String... nombres) {
        this.nombres = nombres;
        this.argumentos = new ArrayList<>();
    }

    public Comando addArgumento(String arg){
        this.argumentos.add(arg);
        return this;
    }

    public String getNombres() {
        String str = "";
        for (String nombre : nombres) {
            str += nombre + ",";
        }
        
        return str.substring(0, str.length()-1);
    }
    
    public boolean isNombre(String nombre){
        for (String n : nombres) {
            if(n.equals(nombre)){
                return true;
            }
        }
        
        return false;
    }

    public ArrayList<String> getArgumentos() {
        return argumentos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Comando setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }
    
    public Comando setEjecucion(Ejecutable exec){
        this.ejecutable = exec;
        return this;
    }
    
    public void ejecutar(String[] argumentos){
        this.ejecutable.ejecutar(argumentos);
    }
}
