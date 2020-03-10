package conexionCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import paquetes.Paquete;
import paquetes.PaqueteElementos;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteSolicitud;
import paquetes.PaqueteTablero;

public class HiloCliente extends Thread {

    private Cliente cliente;
    private Socket socket;
    private AdministradorEntradas adminEntradas;

    public HiloCliente(Cliente cliente, Socket socket, AdministradorEntradas adminEntradas) {
        this.cliente = cliente;
        this.socket = socket;
        this.adminEntradas = adminEntradas;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
                Paquete paquete;

                try {
                    paquete = (Paquete) entrada.readObject();
                    if (paquete != null) {
                        if (paquete instanceof PaqueteJugada) {
                            PaqueteJugada paqueteJugada = (PaqueteJugada) paquete;
                            adminEntradas.actualizarJugada(paqueteJugada);
                        }
                        if(paquete instanceof PaqueteElementos){
                            PaqueteElementos paqueteElementos = (PaqueteElementos) paquete;
                            adminEntradas.actualizarAbadonoJugador(paqueteElementos);
                        }
                        if(paquete instanceof PaqueteNuevoJugador){
                            PaqueteNuevoJugador paqueteNuevoJugador = (PaqueteNuevoJugador) paquete;
                            adminEntradas.actualizarNuegoJugador(paqueteNuevoJugador);
                        }                        
                        if(paquete instanceof PaqueteSolicitud){
                            PaqueteSolicitud paqueteSolicitud = (PaqueteSolicitud) paquete;
                            
                            if(paqueteSolicitud.obtenerSolicitud().equals("iniciar")){
                                adminEntradas.iniciarPartida(paqueteSolicitud.obtenerJugador());
                            } 
                            if(paqueteSolicitud.obtenerSolicitud().equals("elegir tablero")){
                                adminEntradas.elegirTablero();
                            }
                            if(paqueteSolicitud.obtenerSolicitud().equals("turno")){
                                adminEntradas.actualizarTurno(paqueteSolicitud.obtenerJugador());
                            }
                            if(paqueteSolicitud.obtenerSolicitud().equals("finalizar")){
                                adminEntradas.finalizarPartida();
                            }
                            if(paqueteSolicitud.obtenerSolicitud().equals("confirmo conexion")){
                                adminEntradas.confirmarConexion(paqueteSolicitud);
                            }
                        }
                        if(paquete instanceof PaqueteTablero){
                            PaqueteTablero paqueteTablero = (PaqueteTablero) paquete;
                            adminEntradas.establecerTamañoTablero(paqueteTablero);
                        }
                        
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("HiloCliente, run:   " + ex.getMessage());
                }
            }
        } catch (IOException ex) {
            System.out.print("HiloServidor, Run:     " + ex.getMessage());
        }
    }

}
