package modelos;

import conexionCliente.Cliente;
import conexionCliente.AdministradorSalidas;
import dominio.Cuadro;
import dominio.ElementoTablero;
import dominio.Jugador;
import dominio.Linea;
import dominio.Partida;
import java.awt.Point;
import java.util.ArrayList;
import paquetes.Paquete;
import paquetes.PaqueteElementos;
import paquetes.PaqueteEstado;
import paquetes.PaqueteSolicitud;
import paquetes.PaqueteTablero;
import vistas.Vista;
import vistas.VistaTablero;

public class ModeloTablero implements ModeloObservable {

    private Vista vista;
    private Partida partida;
    private ArrayList<Object[]> ubicacionLineas = new ArrayList<>();
    private ArrayList<Object[]> ubicacionCuadros = new ArrayList<>();
    private Jugador propietario;
    private Cliente cliente;
    private AdministradorSalidas adminSalidas;
    private ArrayList<ColorJugador> coloresProvisionales = new ArrayList<>();

    public ModeloTablero(Partida partida, Cliente cliente) {
        this.partida = partida;
        this.cliente = cliente;
        adminSalidas = new AdministradorSalidas(cliente);
        coloresProvisionales.add(new ColorJugador("rojo"));
        coloresProvisionales.add(new ColorJugador("amarillo"));
        coloresProvisionales.add(new ColorJugador("azul"));
        coloresProvisionales.add(new ColorJugador("verde"));
    }

    public Jugador obtenerPropietario() {
        return propietario;
    }

    public void actualizarTurno(Jugador jugador) {
        partida.obtenerTurno().establecerTurnoActual(jugador);
        PaqueteEstado paquete = new PaqueteEstado(jugador, partida.obtenerEstadoPartida());
        notificar(paquete);
    }

    public void establecerPropietario(Jugador jugador) {
        propietario = jugador;

        String color = obtenerColorDisponible();
        actualizarColorJugador(jugador, color);
        establecerColorProvisional(jugador, color);
    }

    public Partida obtenerPartida() {
        return partida;
    }

    public void establecerUbicacionElementos() {
        establecerUbicacionCuadros();
        establecerUbicacionLineas();
        PaqueteTablero paquete = new PaqueteTablero(propietario, ubicacionLineas, ubicacionCuadros, partida.obtenerTablero().obtenerTamañoTablero());
        notificar(paquete);
        adminSalidas.notificarTablero(propietario, ubicacionLineas, ubicacionCuadros, partida.obtenerTablero().obtenerTamañoTablero());
        adminSalidas.enviarPaquete(paquete);
    }

    public void agregarNuevoJugador(Jugador jugador) {
        partida.obtenerTurno().agregarJugador(jugador);
        partida.obtenerTurno().establecerOrden();
        String color = obtenerColorDisponible();
        actualizarColorJugador(jugador, color);
        establecerColorProvisional(jugador, color);

        Paquete paquete = adminSalidas.notificarNuevoJugador(jugador);
        if (paquete.obtenerJugador() != propietario) {
            notificar(paquete);
        } else {
            adminSalidas.enviarPaquete(paquete);
        }
    }

    public void establecerColorProvisional(Jugador jugador, String color) {
        for (ColorJugador colorJugador : coloresProvisionales) {
            if (colorJugador.obtenerColor().equals(color)) {
                colorJugador.establecerJugador(jugador);
            } else {
                if (colorJugador.obtenerJugador() != null) {
                    if (jugador != null) {
                        if (colorJugador.obtenerJugador().obtenertNickname().equals(jugador.obtenertNickname())) {
                            colorJugador.establecerJugador(null);
                        }
                    }

                }
            }
        }

        // Se lo notifico a la vista para que deseleccione el color necesario
        if (jugador != null) {
            Jugador ficticio = new Jugador(jugador.obtenertNickname(), jugador.obtenerAvatar());
            ficticio.establecerColor(color);
            PaqueteElementos paqueteElementos = new PaqueteElementos(ficticio, null, "seleccion");
            notificar(paqueteElementos);
        } 
    }

    public String obtenerColorDisponible() {
        for (ColorJugador colorJugador : coloresProvisionales) {
            if (colorJugador.obtenerJugador() == null) {
                return colorJugador.obtenerColor();
            }
        }
        return null;
    }

    public String[] obtenerColorJugadores() {
        String coloresJugadores[] = new String[partida.obtenerTurno().obtenerJugadores().length];
        int indice = 0;

        for (Jugador jugador : partida.obtenerTurno().obtenerJugadores()) {
            coloresJugadores[indice] = jugador.obtenerColor();
            indice++;
        }
        return coloresJugadores;
    }

    public void notificarJugadorListo(Jugador jugador) {
        PaqueteSolicitud paquete = new PaqueteSolicitud(jugador, "jugador listo");
        adminSalidas.enviarPaquete(paquete);
    }

    public void establecerUbicacionCuadros() {
        ArrayList<ElementoTablero> elementos = partida.obtenerTablero().obtenerElementosTablero();
        int tamañoTablero = partida.obtenerTablero().obtenerTamañoTablero();

        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Cuadro) {
                Cuadro cuadro = (Cuadro) elemento;
                int fila = cuadro.obtenerFilaCuadricula();
                int columna = cuadro.obtenerColumnaCuadricula();
                Point posicion = null;

                switch (tamañoTablero) {
                    case 10:
                        posicion = new Point(8 + (fila * 64), 8 + (columna * 64));
                        break;
                    case 20:
                        posicion = new Point(8 + (fila * 32), 8 + (columna * 32));
                        break;
                    case 40:
                        posicion = new Point(6 + (fila * 16), 6 + (columna * 16));
                        break;
                }

                Object[] arreglo = {cuadro, posicion};
                ubicacionCuadros.add(arreglo);
            }
        }
    }

    public void establecerUbicacionLineas() {
        ArrayList<ElementoTablero> elementos = partida.obtenerTablero().obtenerElementosTablero();
        int tamañoTablero = partida.obtenerTablero().obtenerTamañoTablero();

        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Cuadro) {
                Cuadro cuadro = (Cuadro) elemento;
                int fila = cuadro.obtenerFilaCuadricula();
                int columna = cuadro.obtenerColumnaCuadricula();

                switch (tamañoTablero) {
                    case 10:
                        // HORIZONTAL
                        if (fila == 0) {
                            establecerUbicacionLinea(cuadro, "superior", (fila * 64), 8 + (columna * 64), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "inferior", ((fila + 1) * 64), 8 + (columna * 64), new Point(fila + 1, columna));
                        // VERTICAL
                        if (columna == 0) {
                            establecerUbicacionLinea(cuadro, "izquierda", 8 + (fila * 64), (columna * 64), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "derecha", 8 + (fila * 64), ((columna + 1) * 64), new Point(fila, columna + 1));
                        break;
                    case 20:
                        // HORIZONTAL
                        if (fila == 0) {
                            establecerUbicacionLinea(cuadro, "superior", (fila * 32), 8 + (columna * 32), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "inferior", ((fila + 1) * 32), 8 + (columna * 32), new Point(fila + 1, columna));
                        // VERTICAL
                        if (columna == 0) {
                            establecerUbicacionLinea(cuadro, "izquierda", 8 + (fila * 32), (columna * 32), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "derecha", 8 + (fila * 32), ((columna + 1) * 32), new Point(fila, columna + 1));
                        break;
                    case 40:
                        // HORIZONTAL
                        if (fila == 0) {
                            establecerUbicacionLinea(cuadro, "superior", (fila * 16), 6 + (columna * 16), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "inferior", ((fila + 1) * 16), 6 + (columna * 16), new Point(fila + 1, columna));
                        // VERTICAL
                        if (columna == 0) {
                            establecerUbicacionLinea(cuadro, "izquierda", 6 + (fila * 16), (columna * 16), new Point(fila, columna));
                        }
                        establecerUbicacionLinea(cuadro, "derecha", 6 + (fila * 16), ((columna + 1) * 16), new Point(fila, columna + 1));
                        break;
                }
            }
        }
    }

    public void establecerUbicacionLinea(Cuadro cuadro, String orientacion, int x, int y, Point coordenada) {
        Linea linea = cuadro.obtenerLinea(orientacion);
        linea.establecerCoordenada(coordenada.x, coordenada.y);
        String tipo;
        Point ubicacion = new Point(x, y);
        if ("superior".equals(orientacion) || "inferior".equals(orientacion)) {
            tipo = "horizontal";
        } else {
            tipo = "vertical";
        }
        Object[] arreglo = {linea, ubicacion, tipo, coordenada};
        ubicacionLineas.add(arreglo);
    }

    public void actualizarColorJugador(Jugador jugador, String color) {
        jugador.establecerColor(color);
        for (ColorJugador colorJugador : coloresProvisionales) {
            if (colorJugador.obtenerColor().equals(color)) {
                colorJugador.establecerJugador(jugador);
            } else {
                if (colorJugador.obtenerJugador() != null) {
                    if (colorJugador.obtenerJugador().obtenertNickname().equals(jugador.obtenertNickname())) {
                        colorJugador.establecerJugador(null);
                    }
                }
            }
        }
    }

    public boolean todosTienenColorAsignado() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (ColorJugador colorJugador : coloresProvisionales) {
            if (colorJugador.obtenerJugador() != null) {
                jugadores.add(colorJugador.obtenerJugador());
            }
        }

        if (jugadores.size() < partida.obtenerTurno().obtenerJugadores().length) {
            return false;
        }
        return true;
    }

    public void actualizarColorJugadores() {
        for (ColorJugador colorJugador : coloresProvisionales) {
            if (colorJugador.obtenerJugador() != null) {
                actualizarColorJugador(colorJugador.obtenerJugador(), colorJugador.obtenerColor());
                ArrayList<ElementoTablero> elementos = partida.obtenerTablero().obtenerElementosJugador(colorJugador.obtenerJugador());
                if (elementos.isEmpty()) {} else {
                    Paquete paquete = new PaqueteElementos(colorJugador.obtenerJugador(), elementos, "cambio");
                    notificar(paquete);
                }
            }
        }
    }

    public void iniciarPartida() {
        partida.iniciarPartida();
    }

    public void finalizarPartida() {
        partida.finalizarPartida();
        PaqueteEstado paqueteEstado = new PaqueteEstado(null, partida.obtenerEstadoPartida());
        notificar(paqueteEstado);
    }

    public void marcarJugada(Jugador jugador, int filaCuadro, int columnaCuadro, String orientacion) {
        for (Jugador j : partida.obtenerTurno().obtenerJugadores()) {
            if (j.obtenertNickname().equals(jugador.obtenertNickname())) {
                jugador = j; // Hacemos que el jugador tome su color asignado
                break;
            }
        }

        Cuadro cuadro = partida.obtenerTablero().obtenerCuadro(filaCuadro, columnaCuadro);
        Cuadro cuadrosGanados[] = partida.obtenerTablero().asignarPropLinea(cuadro, orientacion, jugador);
        Linea linea = partida.obtenerTablero().obtenerCuadro(filaCuadro, columnaCuadro).obtenerLinea(orientacion);
        Paquete paquete;

        if (cuadrosGanados[1] == null) {
            if (cuadrosGanados[0] == null) {
                ElementoTablero[] elementosJugada = {linea};
                paquete = adminSalidas.notificarJugada(elementosJugada, jugador, partida.obtenerEstadoPartida(), partida.obtenerTablero().noHayCuadrosDisponibles());
            } else {
                ElementoTablero elementosJugada[] = {linea, cuadrosGanados[0]};
                paquete = adminSalidas.notificarJugada(elementosJugada, jugador, partida.obtenerEstadoPartida(), partida.obtenerTablero().noHayCuadrosDisponibles());
            }
        } else {
            ElementoTablero elementosJugada[] = {linea, cuadrosGanados[0], cuadrosGanados[1]};
            paquete = adminSalidas.notificarJugada(elementosJugada, jugador, partida.obtenerEstadoPartida(), partida.obtenerTablero().noHayCuadrosDisponibles());
        }

        notificar(paquete);
        if (jugador == propietario) {
            adminSalidas.enviarPaquete(paquete);
        }

    }

    public void abandonarPartida(Jugador jugador) {
        if (jugador == propietario) {
            Paquete paquete = adminSalidas.notificarAbandonoJugador(jugador, partida.obtenerTablero().obtenerElementosJugador(jugador), "abandono");
            adminSalidas.enviarPaquete(paquete);
            notificar(paquete);
        } else {
            partida.obtenerTablero().eliminarElementos(jugador);
            partida.abandonarPartida(jugador);

            Jugador buscado = partida.obtenerTurno().obtenerJugador(jugador.obtenertNickname());
            partida.obtenerTurno().eliminarJugador(buscado);
        }
    }

    @Override
    public void attach(Vista vista) {
        this.vista = vista;
    }

    @Override
    public void detach() {
        VistaTablero vistaTablero = (VistaTablero) vista;
        vistaTablero.dispose();
        this.vista = null;
    }

    @Override
    public void notificar(Paquete paquete) {
        if (vista == null) {} else {
            vista.actualizar(paquete);
        }

    }

}
