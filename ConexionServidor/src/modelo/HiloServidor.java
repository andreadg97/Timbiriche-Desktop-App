package modelo;

import controlador.ControladorServidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import paquetes.Paquete;
import paquetes.PaqueteElementos;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteSolicitud;
import paquetes.PaqueteTablero;

public class HiloServidor extends Thread {

    private Servidor servidorPadre;
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private ControladorServidor controlador;
    private int ID;

    public HiloServidor(Servidor servidorPadre, ControladorServidor controlador, Socket socket, int ID) {
        this.servidorPadre = servidorPadre;
        this.controlador = controlador;
        this.socket = socket;
        this.ID = ID;

        this.start();
    }

    public void confirmarConexion() {
        controlador.confirmarConexion(ID);
    }

    @Override
    public void run() {
        while (socket.isClosed() == false) {
            if (socket.isClosed() == false) {
                escucharMensaje();
            } else {
                break;
            }
        }
    }

    public int obtenerID() {
        return ID;
    }

    public void escucharMensaje() {
        try {
            entrada = new ObjectInputStream(socket.getInputStream());
            Paquete paquete = (Paquete) entrada.readObject();

            if (paquete instanceof PaqueteElementos) {
                if (servidorPadre.obtenerNumeroJugadores() != 1) {
                    PaqueteElementos paqueteElementos = (PaqueteElementos) paquete;
                    controlador.notificarAbandono(paqueteElementos, ID);
                } 
            }
            if (paquete instanceof PaqueteJugada) {
                PaqueteJugada paqueteJugada = (PaqueteJugada) paquete;
                controlador.notificarJugada(paqueteJugada, ID);
            }
            if (paquete instanceof PaqueteNuevoJugador) {
                PaqueteNuevoJugador paqueteNuevoJugador = (PaqueteNuevoJugador) paquete;
                controlador.notificarNuevoJugador(paqueteNuevoJugador, ID);
            }
            if (paquete instanceof PaqueteTablero) {
                PaqueteTablero paqueteTablero = (PaqueteTablero) paquete;
                controlador.establecerTamañoTablero(paqueteTablero);
            }
            if (paquete instanceof PaqueteSolicitud) {
                PaqueteSolicitud paqueteSolicitud = (PaqueteSolicitud) paquete;
                controlador.agregarJugadorListo(ID);
            }

        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("HiloServidor, escucharMensaje:       " + ex.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.print("HiloServidor, cerrarConexion:      " + ex.getMessage());
        }
    }

    public ObjectOutputStream obtenerOutputStream() {
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            return salida;
        } catch (IOException ex) {
            System.out.println("HiloServidor, obtenerOutputStream:       " + ex.getMessage());
        }

        return salida;
    }

}
