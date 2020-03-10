package conexionCliente;

import dominio.ElementoTablero;
import dominio.Jugador;
import dominio.Linea;
import javax.swing.JOptionPane;
import modelos.ModeloObservable;
import modelos.ModeloRegistro;
import modelos.ModeloResultados;
import modelos.ModeloTablero;
import paquetes.PaqueteElementos;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteSolicitud;
import paquetes.PaqueteTablero;

public class AdministradorEntradas {

    private ModeloRegistro modeloRegistro;
    private ModeloTablero modeloTablero;
    private ModeloResultados modeloResultados;

    public void agregarModelo(ModeloObservable modelo) {
        if (modelo instanceof ModeloRegistro) {
            ModeloRegistro mRegistro = (ModeloRegistro) modelo;
            modeloRegistro = mRegistro;
        }
        if (modelo instanceof ModeloTablero) {
            ModeloTablero mTablero = (ModeloTablero) modelo;
            modeloTablero = mTablero;
        }
        if (modelo instanceof ModeloResultados) {
            ModeloResultados mResultados = (ModeloResultados) modelo;
            modeloResultados = mResultados;
        }
    }

    public void confirmarConexion(PaqueteSolicitud paquete){
        modeloRegistro.notificar(paquete);        
    }
    
    public void finalizarPartida(){
        modeloTablero.finalizarPartida();
        modeloTablero.detach();
        
        Jugador jugadores[] = modeloTablero.obtenerPartida().obtenerTurno().obtenerJugadores();
        modeloResultados.establecerJugadores(jugadores);
    }
    
    public void iniciarPartida(Jugador turnoActual) {
        modeloTablero.iniciarPartida();
        modeloTablero.actualizarTurno(turnoActual);
    }

    public void actualizarTurno(Jugador turnoActual) {
        modeloTablero.actualizarTurno(turnoActual);
    }

    public void elegirTablero() {
        String[] tamaños = {"10x10", "20x20", "40x40"};
        int opcion = JOptionPane.showOptionDialog(null, "Tamaño del tablero", "Tamaño del tablero", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, tamaños, null);
        int tamañoTablero;
        switch (opcion) {
            case 0:
                tamañoTablero = 10;
                break;
            case 1:
                tamañoTablero = 20;
                break;
            case 2:
                tamañoTablero = 40;
                break;
            default:
                tamañoTablero = 0;
        }

        modeloTablero.obtenerPartida().obtenerTablero().establecerTamañoTablero(tamañoTablero);
        modeloTablero.establecerUbicacionElementos();
    }

    public void establecerTamañoTablero(PaqueteTablero paquete) {
        modeloTablero.obtenerPartida().obtenerTablero().establecerTamañoTablero(paquete.obtenerTamanioTablero());
        modeloTablero.establecerUbicacionElementos();
    }

    public void actualizarJugada(PaqueteJugada paquete) {
        if (paquete.obtenerJugador().obtenertNickname().equals(modeloTablero.obtenerPropietario().obtenertNickname()) == false) {

            for (ElementoTablero elemento : paquete.obtenerElementosJugada()) {
                if (elemento instanceof Linea) {
                    Linea linea = (Linea) elemento;
                    int fila = linea.obtenerCoordenada()[0];
                    int columna = linea.obtenerCoordenada()[1];
                    String orientacion;

                    if (linea.obtenerTamaño()[0] > linea.obtenerTamaño()[1]) { //Horizontal
                        if (fila == modeloTablero.obtenerPartida().obtenerTablero().obtenerTamañoTablero()) {
                            orientacion = "inferior";
                            fila -= 1;
                        } else {
                            orientacion = "superior";
                        }
                    } else { // Vertical
                        if (columna == modeloTablero.obtenerPartida().obtenerTablero().obtenerTamañoTablero()) {
                            orientacion = "derecha";
                            columna -= 1;
                        } else {
                            orientacion = "izquierda";
                        }
                    }
                    modeloTablero.marcarJugada(paquete.obtenerJugador(), fila, columna, orientacion);
                }
            }
        }
    }

    public void actualizarNuegoJugador(PaqueteNuevoJugador paquete) {
        modeloTablero.agregarNuevoJugador(paquete.obtenerJugador());
    }

    public void agregarPropietario(Jugador jugador) {
        modeloTablero.establecerPropietario(jugador);
    }

    public void actualizarAbadonoJugador(PaqueteElementos paquete) {
        modeloTablero.abandonarPartida(paquete.obtenerJugador());
        modeloTablero.notificar(paquete);
    }

}
