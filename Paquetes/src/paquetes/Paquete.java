package paquetes;
import dominio.Jugador;
import java.io.Serializable;

public class Paquete implements Serializable{
    
    protected Jugador jugador;

    Paquete(Jugador jugador){
        this.jugador = jugador;
    }
    
    public Jugador obtenerJugador() {
        return jugador;
    }
    
}