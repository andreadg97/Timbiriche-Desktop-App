package conexionCliente;

import dominio.ElementoTablero;
import dominio.Jugador;
import java.util.ArrayList;
import paquetes.Paquete;
import paquetes.PaqueteElementos;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteTablero;

public class AdministradorSalidas {

    private Cliente cliente;

    public AdministradorSalidas(Cliente cliente) {
        this.cliente = cliente;
    }

    public Paquete notificarJugada(ElementoTablero elementosJugada[], Jugador jugador, String estadoPartida, boolean noHayCuadrosDisponibles) {
        Paquete paquete = new PaqueteJugada(elementosJugada, jugador, estadoPartida,noHayCuadrosDisponibles);
        return paquete;
    }

    public Paquete notificarNuevoJugador(Jugador jugador) {
        Paquete paquete = new PaqueteNuevoJugador(jugador);
        return paquete;
    }

    public Paquete notificarAbandonoJugador(Jugador jugador, ArrayList<ElementoTablero> elementosJugador, String accion) {
        Paquete paquete = new PaqueteElementos(jugador, elementosJugador, accion);
        return paquete;
    }

    public Paquete notificarTablero(Jugador jugador, ArrayList<Object[]> lineas, ArrayList<Object[]> cuadros, int tamañoTablero) {
        Paquete paquete = new PaqueteTablero(jugador, lineas, cuadros, tamañoTablero);
        return paquete;
    }

    public void enviarPaquete(Paquete paquete) {
        cliente.enviarMensaje(paquete);
    }

}
