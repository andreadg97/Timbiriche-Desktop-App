package modelos;
import dominio.Jugador;

public class ColorJugador {
    
    private String color;
    private Jugador jugador = null;
    
    public ColorJugador(String color){
        this.color = color;
    }
    
    public void establecerJugador(Jugador jugador){
        this.jugador = jugador;
    }
    
    public String obtenerColor(){
        return color;
    }
    
    public Jugador obtenerJugador(){
        return jugador;
    }
}