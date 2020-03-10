package paquetes;

import dominio.ElementoTablero;
import dominio.Jugador;
import java.io.Serializable;
import java.util.ArrayList;

public class PaqueteElementos extends Paquete implements Serializable{
    
    private ArrayList<ElementoTablero> elementosJugador;
    private String accion;
    
    public PaqueteElementos(Jugador jugador, ArrayList<ElementoTablero> elementosJugador, String accion){
        super(jugador);
        this.elementosJugador = elementosJugador;
        this.accion = accion;
    }

    public ArrayList<ElementoTablero> obtenerElementosJugador() {
        return elementosJugador;
    }
    
    public String obtenerAccion(){
        return accion;
    }
    
}