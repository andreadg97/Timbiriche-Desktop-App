package dominio;

import java.io.Serializable;
import java.util.ArrayList;

public class Turno implements Serializable {

    private ArrayList<Jugador> ordenTurno = new ArrayList<>();
    private Jugador turnoActual;

    public void agregarJugador(Jugador jugador) {
        ordenTurno.add(jugador);
    }

    public boolean nombreValido(String nombre) {
        if (nombre.length() > 20) {
            return false;
        }

        if (nombre.isEmpty()) {
            return false;
        }

        for (Jugador jugador : ordenTurno) {
            if (jugador.obtenertNickname().equals(nombre)) {
                return false;
            }
        }

        return true;
    }

    public Jugador obtenerTurnoActual() {
        return turnoActual;
    }

    public int obtenerTurnoJugador(String nickname) {
        for (Jugador jugador : ordenTurno) {
            if (nickname.equals(jugador.obtenertNickname())) {
                return ordenTurno.indexOf(jugador);
            }
        }
        System.out.println("El jugador " + nickname + " no esta en el turno");
        return -1;
    }

    public Jugador obtenerJugador(String nickname) {
        for (Jugador jugador : ordenTurno) {
            if (jugador.obtenertNickname().equals(nickname)) {
                return jugador;
            }
        }

        return null;
    }

    public Jugador[] obtenerJugadores() {
        Jugador jugadores[] = new Jugador[ordenTurno.size()];

        int contador = 0;
        for (Jugador jugador : ordenTurno) {
            jugadores[contador] = jugador;
            contador++;
        }

        return jugadores;
    }

    public void actualizarTurnoActual() {
        int indice = 0;

        for (Jugador jugador : ordenTurno) {
            if (jugador.equals(turnoActual)) {
                indice = ordenTurno.indexOf(jugador);
            }
        }

        if (indice == (ordenTurno.size() - 1)) {
            turnoActual = ordenTurno.get(0);
        } else {
            turnoActual = ordenTurno.get(indice + 1);
        }
    }

    public void establecerTurnoActual(Jugador jugador) {
        this.turnoActual = jugador;
    }

    public void eliminarJugador(Jugador jugador) {
        /*if (ordenTurno.size() > 0) {
            System.out.println("Hay "+ordenTurno.size()+" jugadores en el turno");
            for (Jugador j : ordenTurno) {
                if (j.obtenertNickname().equals(jugador.obtenertNickname())) {
                    ordenTurno.remove(j);
                }
            }
        } else {
            System.out.println("El size es 0");
        }*/

        ordenTurno.remove(jugador);
    }
    
    public int obtenerNumeroJugadores() {
        return ordenTurno.size();
    }

    public int generarNumeroRandom() {
        int numero = (int) (Math.random() * ordenTurno.size() + 0);
        return numero;
    }

    public void establecerOrden() {
        Jugador ordenNuevo[] = new Jugador[obtenerNumeroJugadores()];
        boolean valido;

        for (Jugador jugador : ordenTurno) {
            valido = false;
            while (valido == false) {
                int indice = generarNumeroRandom();
                Jugador actualIndice = ordenNuevo[indice];

                if (actualIndice == null) {
                    ordenNuevo[indice] = jugador;
                    valido = true;
                }
            }
        }

        ordenTurno.clear();
        for (Jugador jugador : ordenNuevo) {
            ordenTurno.add(jugador);
        }

        turnoActual = ordenTurno.get(0);
    }

}
