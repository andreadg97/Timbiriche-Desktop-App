package dominio;

import java.io.Serializable;

public class Cuadro extends ElementoTablero implements Serializable{
    
    private Linea lineaIzq = null;
    private Linea lineaDer = null;
    private Linea lineaSup = null;
    private Linea lineaInf = null;
    int filaCuadricula;
    int columnaCuadricula;
    
    public Cuadro(int filaCuadricula, int columnaCuadricula){
        this.filaCuadricula = filaCuadricula;
        this.columnaCuadricula = columnaCuadricula;
    }
    
    public void asignarTamaño(int tamañoLado){
        ancho = tamañoLado;
        alto = ancho;
    }
    
    public int obtenerColumnaCuadricula(){
        return columnaCuadricula;
    }
    
    public int obtenerFilaCuadricula(){
        return filaCuadricula;
    }
    
    public Linea obtenerLinea(String orientacion){
        switch(orientacion){
            case "superior":
                return lineaSup;
            case "inferior":
                return lineaInf;
            case "izquierda":
                return lineaIzq;
            case "derecha":
                return lineaDer;
            default: 
                return null;
        }
    }
    
    public void agregarLinea(String orientacion, Linea linea){
        switch(orientacion){
            case "superior":
                lineaSup = linea;
                break;
            case "inferior":
                lineaInf = linea;
                break;
            case "izquierda":
                lineaIzq = linea;
                break;
            case "derecha":
                lineaDer = linea;
                break;
        }
    }
    
}