package paquetes;
import dominio.Jugador;
import java.io.Serializable;

public class PaqueteSolicitud extends Paquete implements Serializable{
    
    private String solicitud;
    
    public PaqueteSolicitud(Jugador jugador, String solicitud) {
        super(jugador);
        this.solicitud = solicitud;
    }
    
    public String obtenerSolicitud(){
        return solicitud;
    }
    
}