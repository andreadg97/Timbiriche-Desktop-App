package paquetes;

import dominio.ElementoTablero;
import dominio.Jugador;
import java.io.Serializable;

public class PaqueteJugada extends Paquete implements Serializable{
    
    private ElementoTablero[] elementosJugada;
    private String estadoPartida;
    private boolean noHayCuadrosDisponibles;
    
    public PaqueteJugada(ElementoTablero elementosJugada[], Jugador jugador, String estadoPartida, boolean noHayCuadrosDisponibles){
        super(jugador);
        this.elementosJugada = elementosJugada;
        this.estadoPartida = estadoPartida;
        this.noHayCuadrosDisponibles = noHayCuadrosDisponibles;
    }
    
    public ElementoTablero[] obtenerElementosJugada() {
        return elementosJugada;
    }

    public String obtenerEstadoPartida() {
        return estadoPartida;
    }    
    
    public boolean noHayCuadrosDisponibles(){
        return  noHayCuadrosDisponibles;
    }
    
}