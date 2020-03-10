package modelos;

import conexionCliente.AdministradorEntradas;
import conexionCliente.Cliente;
import conexionCliente.AdministradorSalidas;
import dominio.Jugador;
import dominio.Partida;
import java.util.ArrayList;
import paquetes.Paquete;
import vistas.Vista;
import vistas.VistaRegistro;

public class ModeloRegistro implements ModeloObservable {

    private Cliente cliente;
    private Partida partida;
    private VistaRegistro vista;
    private String nickname = "";
    private int avatar = -1;
    private AdministradorSalidas adminSalidas;
    private AdministradorEntradas adminEntradas;

    public ModeloRegistro(Partida partida, Cliente cliente, AdministradorEntradas adminEntradas) {
        this.partida = partida;
        this.cliente = cliente;
        this.adminSalidas = new AdministradorSalidas(cliente);
        this.adminEntradas = adminEntradas;
    }

    public void registrarJugador() {
        Jugador jugador = new Jugador(nickname, avatar);
        partida.obtenerTurno().agregarJugador(jugador);
        partida.obtenerTurno().establecerOrden();

        //Creo y envio el paquete al servidor
        Paquete paquete = adminSalidas.notificarNuevoJugador(jugador);
        adminSalidas.enviarPaquete(paquete);
        adminEntradas.agregarPropietario(jugador);

        vista.dispose();
    }

    public boolean validarNickname() {
        boolean nombreValido = partida.obtenerTurno().nombreValido(nickname);
        return nombreValido;
    }

    public boolean validarAvatar() {
        ArrayList<Integer> avataresValidos = new ArrayList<>();
        avataresValidos.add(1);
        avataresValidos.add(2);
        avataresValidos.add(3);
        avataresValidos.add(4);

        if (avataresValidos.contains(avatar)) {
            return true;
        } else {
            return false;
        }
    }

    public void establecerNickname(String nickname) {
        this.nickname = nickname;
    }

    public void establecerAvatar(int avatar) {
        this.avatar = avatar;
    }

    @Override
    public void attach(Vista vista) {
        this.vista = (VistaRegistro) vista;
    }

    @Override
    public void detach() {
        this.vista = null;
    }

    @Override
    public void notificar(Paquete paquete) {
        if (vista != null) {
            vista.actualizar(paquete);
        } else {
            System.out.println("es nula");
        }
    }
}
