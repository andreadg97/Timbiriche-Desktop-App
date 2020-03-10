package paquetes;
import dominio.Jugador;
public class PaqueteResultados extends Paquete{
    
    private Jugador[] jugadores; 
    private Jugador[] ganadores;
    
    public PaqueteResultados(Jugador jugador,Jugador jugadores[], Jugador ganadores[]) {
        super(jugador);
        this.jugadores = jugadores;
        this.ganadores = ganadores;
    }
    
    public Jugador[] obtenerJugadores(){
        return jugadores;
    }
    
    public Jugador[] obtenerGanadores(){
        return ganadores;
    }
    
}