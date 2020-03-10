package paquetes;

import dominio.Jugador;
import java.io.Serializable;

public class PaqueteNuevoJugador extends Paquete implements Serializable{
        
    public PaqueteNuevoJugador(Jugador jugador){
        super(jugador);
    }
    
}