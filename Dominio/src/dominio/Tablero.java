package dominio;

import java.io.Serializable;
import java.util.ArrayList;

public class Tablero implements Serializable{

    private ArrayList<ElementoTablero> elementos = new ArrayList<>();
    private int tamañoTablero;
    private int cuadrosOcupados = 0;

    public void establecerTamañoTablero(int tamañoTablero){
        this.tamañoTablero = tamañoTablero;
        crearElementos();
    }
    
    public Linea crearLinea(int tablero, String orientacion) {
        int anchoLineasV = 0, largoLineasV = 0, anchoLineasH = 0,
                largoLineasH = 0, ladoCuadros = 0;
        Linea linea = new Linea();

        if (orientacion.equals("h")) {
            switch (tamañoTablero) {
                case 10:
                    anchoLineasH = 56;
                    largoLineasH = 8;
                    break;
                case 20:
                    anchoLineasH = 22;
                    largoLineasH = 8;
                    break;
                case 40:
                    anchoLineasH = 11;
                    largoLineasH = 6;
                    break;
            }
            linea.establecerTamaño(anchoLineasH, largoLineasH);
        } else {
            switch (tamañoTablero) {
                case 10:
                    anchoLineasV = 8;
                    largoLineasV = 56;
                    break;
                case 20:
                    anchoLineasV = 8;
                    largoLineasV = 22;
                    break;
                case 40:
                    anchoLineasV = 6;
                    largoLineasV = 11;
                    break;
            }
            linea.establecerTamaño(anchoLineasV, largoLineasV);
        }
        return linea;
    }

    public Cuadro crearCuadro(int fila, int columna, int tablero) {
        int tamañoLados;
        Cuadro cuadro = new Cuadro(fila, columna);

        switch (tablero) {
            case 10:
                cuadro.asignarTamaño(56);
                break;
            case 20:
                cuadro.asignarTamaño(22);
                break;
            case 40:
                cuadro.asignarTamaño(11);
                break;
        }

        return cuadro;
    }

    public void crearElementos() {
        int contadorHorizontales = 1;
        int contadorVerticales = 1;

        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                Cuadro cuadro = crearCuadro(i, j, tamañoTablero);
                elementos.add(cuadro);

                Linea lineaInferior = crearLinea(tamañoTablero, "h");
                elementos.add(lineaInferior);
                cuadro.agregarLinea("inferior", lineaInferior);
                contadorHorizontales++;

                Linea lineaDerecha = crearLinea(tamañoTablero, "v");
                elementos.add(lineaDerecha);
                cuadro.agregarLinea("derecha", lineaDerecha);
                contadorVerticales++;

                if (i == 0) {
                    Linea lineaSuperior = crearLinea(tamañoTablero, "h");
                    elementos.add(lineaSuperior);
                    cuadro.agregarLinea("superior", lineaSuperior);
                    contadorHorizontales++;
                } else {
                    Linea superior = obtenerCuadro(i - 1, j).obtenerLinea("inferior");
                    cuadro.agregarLinea("superior", superior);
                }

                if (j == 0) {
                    Linea lineaIzquierda = crearLinea(tamañoTablero, "v");
                    elementos.add(lineaIzquierda);
                    cuadro.agregarLinea("izquierda", lineaIzquierda);
                    contadorVerticales++;
                } else {
                    Linea lineaIzquierda = obtenerCuadro(i, j - 1).obtenerLinea("derecha");
                    cuadro.agregarLinea("izquierda", lineaIzquierda);
                }
            }
        }
    }

    public Cuadro obtenerCuadro(int fila, int columna) {
        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Cuadro) {
                Cuadro cuadro = (Cuadro) elemento;

                if (cuadro.obtenerColumnaCuadricula() == columna && cuadro.obtenerFilaCuadricula() == fila) {
                    return cuadro;
                }
            }
        }

        return null;
    }

    public Cuadro[] asignarPropLinea(Cuadro cuadro, String orientacion, Jugador propietario) {
        Linea linea = cuadro.obtenerLinea(orientacion);
        Cuadro cuadrosGanados[] = new Cuadro[2];
        if (linea.obtenerPropietario() == null) {
            linea.asignarPropietario(propietario);

            if (seCompletoCuadro(cuadro)) {
                cuadrosGanados[0] = cuadro;
                asignarPropCuadro(cuadro, propietario);
            }

            //Si se ganaron dos cuadros a la vez
            Cuadro cuadro2 = null;
            switch (orientacion) {
                case "superior":
                    if (cuadro.obtenerFilaCuadricula() != 0) {
                        cuadro2 = obtenerCuadro(cuadro.obtenerFilaCuadricula() - 1, cuadro.obtenerColumnaCuadricula());
                    }
                    break;
                case "inferior":
                    if (cuadro.obtenerFilaCuadricula() != (tamañoTablero - 1)) {
                        cuadro2 = obtenerCuadro(cuadro.obtenerFilaCuadricula() + 1, cuadro.obtenerColumnaCuadricula());
                    }
                    break;
                case "izquierda":
                    if (cuadro.obtenerColumnaCuadricula() != 0) {
                        cuadro2 = obtenerCuadro(cuadro.obtenerFilaCuadricula(), cuadro.obtenerColumnaCuadricula() - 1);
                    }
                    break;
                case "derecha":
                    if (cuadro.obtenerColumnaCuadricula() != (tamañoTablero - 1)) {
                        cuadro2 = obtenerCuadro(cuadro.obtenerFilaCuadricula(), cuadro.obtenerColumnaCuadricula() + 1);
                    }
                    break;
            }
            if (cuadro2 != null) {
                if (seCompletoCuadro(cuadro2)) {
                    cuadrosGanados[1] = cuadro2;
                    asignarPropCuadro(cuadro2, propietario);
                }
            }
        } 

        return cuadrosGanados;
    }

    public void asignarPropCuadro(Cuadro cuadro, Jugador jugador) {
        cuadro.asignarPropietario(jugador);
        jugador.sumarPunto();
        cuadrosOcupados++;
    }

    public boolean noHayCuadrosDisponibles() {
        return cuadrosOcupados == tamañoTablero * tamañoTablero;
    }

    public void eliminarElementos(Jugador propietario) {
        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Linea) {
                Linea linea = (Linea) elemento;
                if (linea.obtenerPropietario() != null) {
                    if (linea.obtenerPropietario().equals(propietario)) {
                        linea.asignarPropietario(null);
                    }
                }
            } else {
                Cuadro cuadro = (Cuadro) elemento;
                if (cuadro.obtenerPropietario() != null) {
                    if (cuadro.obtenerPropietario().equals(propietario)) {
                        cuadro.asignarPropietario(null);
                    }
                }
            }
        }
    }

    public boolean seCompletoCuadro(Cuadro cuadro) {
        int ladosOcupados = 0;

        if (cuadro.obtenerLinea("superior").obtenerPropietario() != null) {
            ladosOcupados++;
        }
        if (cuadro.obtenerLinea("inferior").obtenerPropietario() != null) {
            ladosOcupados++;
        }
        if (cuadro.obtenerLinea("izquierda").obtenerPropietario() != null) {
            ladosOcupados++;
        }
        if (cuadro.obtenerLinea("derecha").obtenerPropietario() != null) {
            ladosOcupados++;
        }

        return (ladosOcupados == 4);
    }

    public int obtenerTamañoTablero() {
        return tamañoTablero;
    }

    public ArrayList<ElementoTablero> obtenerElementosJugador(Jugador jugador) {
        ArrayList<ElementoTablero> elementosJugador = new ArrayList<>();
        for (ElementoTablero elemento : elementos) {
            if (elemento.obtenerPropietario() != null) {
                if (elemento.obtenerPropietario().equals(jugador)) {
                    elementosJugador.add(elemento);
                }
            }
        }

        return elementosJugador;
    }

    public ArrayList<ElementoTablero> obtenerElementosTablero() {
        return elementos;
    }
}
