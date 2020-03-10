package dominio;

import java.io.Serializable;

public abstract class ElementoTablero implements Serializable{
    
    protected Jugador propietario = null;
    protected int ancho;
    protected int alto;
    
    public void asignarPropietario(Jugador jugador){
        propietario = jugador;
    }
    
    public Jugador obtenerPropietario(){
        return propietario;
    }
    
    public int[] obtenerTamaño(){
        int dimension[] = {ancho,alto};
        return dimension;
    }
    
}