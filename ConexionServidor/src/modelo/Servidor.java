package modelo;

import controlador.ControladorServidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private Servidor servidor;
    private ArrayList<HiloServidor> clientes = new ArrayList<>();
    private ServerSocket servidorSocket;
    private int PUERTO = 1931;
    private int numeroJugadores = 0;
    private ControladorServidor controlador = new ControladorServidor(this);

    public Servidor obtenerServidor() {
        if (servidor == null) {
            servidor = this;
            try {
                servidorSocket = new ServerSocket(PUERTO);
            } catch (IOException ex) {
                System.out.print("Servidor, constructor:     " + ex.getMessage());
            }
        }
        return servidor;
    }

    public void noRecibirClientes() {
        try {
            servidorSocket.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarHilo(int IDhilo) {
        HiloServidor buscado = null;
        for (HiloServidor hilo : clientes) {
            if (hilo.obtenerID() == IDhilo) {
                buscado = hilo;
            }
        }

        if (buscado != null) {
            clientes.remove(buscado);
            numeroJugadores -= 1;
            buscado.cerrarConexion();
        }
    }

    public void recibirClientes() {
        while (true) {
            if (servidorSocket.isClosed() == false) {
                try {
                    Socket socket = servidorSocket.accept();
                    numeroJugadores++;
                    crearHilo(socket);

                    if (numeroJugadores == 4) {
                        break;
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                break;
            }
        }
        noRecibirClientes();
    }

    public int obtenerNumeroJugadores() {
        return numeroJugadores;
    }

    public void crearHilo(Socket socket) {
        HiloServidor hilo = new HiloServidor(servidor, controlador, socket, numeroJugadores);
        clientes.add(hilo);
        controlador.confirmarConexion(hilo.obtenerID());
    }

    public ArrayList<HiloServidor> obtenerClientes() {
        return clientes;
    }

}
