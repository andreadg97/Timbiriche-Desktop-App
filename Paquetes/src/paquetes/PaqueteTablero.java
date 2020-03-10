package paquetes;
import dominio.Jugador;
import java.io.Serializable;
import java.util.ArrayList;

public class PaqueteTablero extends Paquete implements Serializable{
    
    private int tamanioTablero;
    private ArrayList<Object[]> lineas;
    private ArrayList<Object[]> cuadros;

    public PaqueteTablero(Jugador jugador,ArrayList<Object[]> lineas, ArrayList<Object[]> cuadros, int tamañoTablero) {
        super(jugador);
        this.lineas = lineas;
        this.cuadros = cuadros;
        this.tamanioTablero = tamañoTablero;
    }

    public int obtenerTamanioTablero() {
        return tamanioTablero;
    }

    public ArrayList<Object[]> obtenerLineas() {
        return lineas;
    }

    public ArrayList<Object[]> obtenerCuadros() {
        return cuadros;
    }
    
    public int obtenerTamañoTablero(){
        return tamanioTablero;
    }
    
}
