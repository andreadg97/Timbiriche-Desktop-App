package paquetes;

import dominio.Jugador;

public class PaqueteEstado extends Paquete{
    
    private String estadoPartida;
    
    public PaqueteEstado(Jugador jugador, String estado) {
        super(jugador);
        estadoPartida = estado;
    }
    
    public String obtenerEstado(){
        return estadoPartida;
    }
    
}