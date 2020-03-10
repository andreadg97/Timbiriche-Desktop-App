package dominio;

import java.io.Serializable;

public class Linea extends ElementoTablero implements Serializable{

    private int fila;
    private int columna;
    
    public void establecerTamaño(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public void establecerCoordenada(int fila, int columna){
        this.fila = fila;
        this.columna = columna;
    }
    
    public int[] obtenerCoordenada(){
        int[] coordenada = {fila,columna};
        return coordenada;
    }
    
}