package dominio;

import java.io.Serializable;

public class Partida implements Serializable{
    
    private Turno turno;
    private Tablero tablero;
    private String estadoPartida = "pendiente";

    public Partida(Turno turno,Tablero tablero){
        this.tablero = tablero;
        this.turno = turno;
    }
    
    public Turno obtenerTurno(){
        return turno;
    }
    
    public Tablero obtenerTablero(){
        return tablero;
    }
    
    public void iniciarPartida(){
        this.estadoPartida = "en curso";
    }
    
    public void finalizarPartida(){
        this.estadoPartida = "finalizada";
    }
    
    public String obtenerEstadoPartida(){
        return estadoPartida;
    }
    
    public void abandonarPartida(Jugador jugador){
        tablero.eliminarElementos(jugador);
    }
    
}