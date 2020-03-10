package controlador;

import dominio.Cuadro;
import dominio.ElementoTablero;
import dominio.Jugador;
import dominio.Turno;
import java.io.IOException;
import java.util.ArrayList;
import modelo.HiloServidor;
import modelo.Servidor;
import paquetes.Paquete;
import paquetes.PaqueteElementos;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteSolicitud;
import paquetes.PaqueteTablero;

public class ControladorServidor {

    private Servidor servidor;
    private int tamañoTablero;
    private Turno turno = new Turno();
    private ArrayList<Integer> jugadoresListos = new ArrayList<>();

    public ControladorServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public void establecerTamañoTablero(PaqueteTablero paquete) {
        tamañoTablero = paquete.obtenerTamanioTablero();
    }

    public int obtenerTamañoTablero() {
        return tamañoTablero;
    }

    public void confirmarConexion(int IDcliente) {
        PaqueteSolicitud paquete = new PaqueteSolicitud(new Jugador("Servidor", 0), "confirmo conexion");
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            if (hilo.obtenerID() == IDcliente) {
                try {
                    hilo.obtenerOutputStream().writeObject(paquete);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                System.out.println("No es el mio");
            }
        }
    }

    public void agregarJugadorListo(int ID) {
        jugadoresListos.add(ID);
        boolean listos = jugadoresListos();

        if (listos == true) {
            notificarInicioPartida();
        }
    }

    public boolean jugadoresListos() {
        int numeroJugadores = servidor.obtenerNumeroJugadores();
        if (numeroJugadores > 1 && jugadoresListos.size() == numeroJugadores) {
            return true;
        } else {
            return false;
        }
    }

    public void notificarInicioPartida() {
        turno.establecerOrden();
        Jugador jugador = turno.obtenerTurnoActual();

        PaqueteSolicitud paquete = new PaqueteSolicitud(jugador, "iniciar");
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            try {
                hilo.obtenerOutputStream().writeObject(paquete);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        servidor.noRecibirClientes();
    }

    public void notificarTablero(int ID) {
        PaqueteTablero paquete = new PaqueteTablero(null, null, null, tamañoTablero);
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            if (hilo.obtenerID() == ID) {
                try {
                    hilo.obtenerOutputStream().writeObject(paquete);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public void elegirTamañoTablero(int IDcliente, PaqueteSolicitud paquete) {
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            if (hilo.obtenerID() == IDcliente) {
                try {
                    hilo.obtenerOutputStream().writeObject(paquete);
                    break;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            }
        }
    }

    public void notificarNuevoJugador(PaqueteNuevoJugador paquete, int IDcliente) {
        turno.agregarJugador(paquete.obtenerJugador());

        for (HiloServidor hilo : servidor.obtenerClientes()) {
            if (hilo.obtenerID() != IDcliente) {
                try {
                    hilo.obtenerOutputStream().writeObject(paquete);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        if (IDcliente == 1) {  // Si es el primer jugador, elige tablero
            if (tamañoTablero == 0) {
                PaqueteSolicitud paqueteSolicitud = new PaqueteSolicitud(new Jugador("Servidor", 1), "elegir tablero");
                elegirTamañoTablero(IDcliente, paqueteSolicitud);
            }
        } else { // Si no es el primero, le paso el tablero y los jugadores
            notificarTablero(IDcliente);

            for (HiloServidor hilo : servidor.obtenerClientes()) {
                if (hilo.obtenerID() == IDcliente) {
                    // Pasarle todos los jugadores que llegaron antes que el
                    for (Jugador j : turno.obtenerJugadores()) {
                        if (j.obtenertNickname().equals(paquete.obtenerJugador().obtenertNickname()) == false) {
                            PaqueteNuevoJugador paqueteJugadorAnterior = new PaqueteNuevoJugador(j);
                            try {
                                hilo.obtenerOutputStream().writeObject(paqueteJugadorAnterior);
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    break;
                }
            }
        }

        if (servidor.obtenerNumeroJugadores() == 4) { // Si hay 4 jugadores
            notificarInicioPartida();
        }
    }

    public void notificarJugada(PaqueteJugada paquete, int IDcliente) {
        // Notifico a todos la jugada
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            if (hilo.obtenerID() != IDcliente) {
                try {
                    hilo.obtenerOutputStream().writeObject(paquete);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        if (paquete.noHayCuadrosDisponibles() == false) {
            // Identificar si gano algun cuadro
            boolean ganoCuadro = false;
            if (paquete.obtenerJugador().obtenertNickname().equals(turno.obtenerTurnoActual().obtenertNickname())) {
                System.out.println("Recibi la jugada del jugador en turno");
                ElementoTablero elementos[] = paquete.obtenerElementosJugada();
                for (ElementoTablero elemento : elementos) {
                    if (elemento instanceof Cuadro) {
                        ganoCuadro = true;
                    }
                }
            }

            if (ganoCuadro == true) { // Si gano cuadro repite turno
                PaqueteSolicitud paqueteSolicitud = new PaqueteSolicitud(paquete.obtenerJugador(), "turno");
                for (HiloServidor hilo : servidor.obtenerClientes()) {
                    try {
                        hilo.obtenerOutputStream().writeObject(paquete);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
                // Si no gano cuadro, actualizar el turno y notificarlo
                turno.actualizarTurnoActual();
                System.out.println("El nuevo turno es: " + turno.obtenerTurnoActual().obtenertNickname());
                PaqueteSolicitud paqueteSolicitud = new PaqueteSolicitud(turno.obtenerTurnoActual(), "turno");
                for (HiloServidor hilo : servidor.obtenerClientes()) {
                    try {
                        hilo.obtenerOutputStream().writeObject(paqueteSolicitud);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        } else {
            System.out.println("No hay cuadros disponibles");
            finalizarPartida();
        }
    }

    public void finalizarPartida() {
        PaqueteSolicitud paqueteFin = new PaqueteSolicitud(new Jugador("Servidor", 1), "finalizar");
        for (HiloServidor hilo : servidor.obtenerClientes()) {
            try {
                hilo.obtenerOutputStream().writeObject(paqueteFin);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Notifique a todos de que la partida finalizo");
    }

    public void notificarAbandono(PaqueteElementos paquete, int IDcliente) {
        System.out.println("\n ---> ENTRE AL METODO 'NOTIFICAR ABANDONO'");

        if (servidor.obtenerNumeroJugadores() > 0) {
            System.out.println("Hay " + servidor.obtenerClientes().size() + " clientes");
            System.out.println("Hay " + servidor.obtenerNumeroJugadores() + " jugadores");

            for (HiloServidor hilo : servidor.obtenerClientes()) {
                System.out.println("\nHilo " + hilo.obtenerID());
                if (hilo.obtenerID() != IDcliente) {
                    System.out.println("Su ID es distinta al desertor");
                    try {
                        System.out.println("Le dire que elimine al desertor");
                        hilo.obtenerOutputStream().writeObject(paquete);
                        System.out.println("Le dije al hilo " + hilo.obtenerID() + " que eliminara a " + paquete.obtenerJugador().obtenertNickname());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            System.out.println("\n-> Ya le dije a todos que la eliminaran");

            eliminarJugadorTurno(paquete);

            System.out.println("\nLe dire al servidor que elimine el hilo del desertor");
            servidor.eliminarHilo(IDcliente);

            // Si la partida inicio ya y el jugador se quedo solo, que sea el ganador:
            System.out.println("Voy a checar si queda un solo jugador y la partida esta iniciada");
            if (turno.obtenerJugadores().length > 0) {
                for (Jugador j : turno.obtenerJugadores()) {
                    System.out.println("El turno esta asi: "+j.obtenertNickname());
                }
            }
            if (turno.obtenerTurnoActual() != null) {
                System.out.println("La partida esta iniciada");
                if (turno.obtenerJugadores().length == 1) {
                    System.out.println("Finalizare la partida");
                    finalizarPartida();
                }
            }
        }
    }

    public void eliminarJugadorTurno(Paquete paquete) {
        System.out.println("ENTRE AL METODO 'eliminarJugadorTurno'");
        // Eliminar del turno al jugador
        Jugador desertor = null;
        for (Jugador j : turno.obtenerJugadores()) {
            if (j.obtenertNickname().equals(paquete.obtenerJugador().obtenertNickname())) {
                desertor = j;
            }
        }

        System.out.println("El desertor es " + desertor.obtenertNickname());
        turno.eliminarJugador(desertor); 
        System.out.println("El turno quedo asi:");

        if (turno.obtenerJugadores().length > 0) {
            for (Jugador jugador : turno.obtenerJugadores()) {
                System.out.println(jugador.obtenertNickname());
            }
        }

        // Si era el turno del desertor, actualizar el turno
        System.out.println("Voy a revisar si no era el turno del desertor");
        if (turno.obtenerTurnoActual() != null) {
            if (turno.obtenerTurnoActual().obtenertNickname().equals(desertor.obtenertNickname())) {
                System.out.println("Era el turno del desertor");
                turno.actualizarTurnoActual();
                System.out.println("El nuevo turno es: " + turno.obtenerTurnoActual().obtenertNickname());
                PaqueteSolicitud paqueteSolicitud = new PaqueteSolicitud(turno.obtenerTurnoActual(), "turno");
                for (HiloServidor hilo : servidor.obtenerClientes()) {
                    try {
                        hilo.obtenerOutputStream().writeObject(paqueteSolicitud);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
                System.out.println("No era el turno del desertor");
            }
        } else {
            System.out.println("La partida aun no ha iniciado");
        }
    }

}
