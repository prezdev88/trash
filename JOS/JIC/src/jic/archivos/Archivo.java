package jic.archivos;

import java.util.ArrayList;

public class Archivo {
    private String nombre;
    private boolean archivo;
    private byte[] contenido;
    private ArrayList<Archivo> hijos;

    public Archivo(String nombre, boolean archivo) {
        this.nombre = nombre;
        this.archivo = archivo;
        hijos = new ArrayList<>();
        contenido = new byte[0];
    }
    
    public void addHijo(Archivo a){
        hijos.add(a);
    }

    public ArrayList<Archivo> getHijos() {
        return hijos;
    }
    
    public Archivo existeArchivoHijo(Archivo ar){
        for (Archivo arc : hijos) {
            if(arc.getNombre().equals(ar.getNombre()) && arc.isArchivo() == ar.isArchivo()){
                return arc;
            }
        }
        
        return null;
    }
    
//    public Archivo getArchivo(String nombre){
//        for (Archivo arc : hijos) {
//            if(arc.getNombre().equals(nombre)){
//                return arc;
//            }
//        }
//        return null;
//    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isArchivo() {
        return archivo;
    }

    public void setArchivo(boolean archivo) {
        this.archivo = archivo;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    public long size(){
        return contenido.length;
    }
    
}
