package modelos;

import dominio.Jugador;
import java.util.ArrayList;
import paquetes.Paquete;
import paquetes.PaqueteResultados;
import vistas.Vista;

public class ModeloResultados implements ModeloObservable {

    private Vista vista;
    private Jugador[] ordenPuntajes;
    private ArrayList<Jugador> ganadores = new ArrayList<>();

    public void establecerJugadores(Jugador jugadores[]){
        ordenPuntajes = jugadores;
        ordenarPuntajes();
        establecerGanadores();
        
        Jugador[] losGanadores = new Jugador[ganadores.size()];
        int contador = 0;
        for(Jugador jugador: ganadores){
            losGanadores[contador] = ganadores.get(contador);
            contador++;
        }
        
        PaqueteResultados paqueteResultados = new PaqueteResultados(ordenPuntajes[0], ordenPuntajes, losGanadores);
        notificar(paqueteResultados);
    }
    
    public void ordenarPuntajes() {
        Jugador[] jugadores = ordenPuntajes;
        Jugador[] mayorAMenor = new Jugador[jugadores.length];
        Jugador mayor = null;
        int tamaño = jugadores.length;
        int contador = 0;
        int indiceMayor = 0;

        while (tamaño > 0) {
            for(int i=0; i<jugadores.length; i++){
                if(jugadores[i]!=null){
                    mayor = jugadores[i];
                    indiceMayor = i;
                    break;
                }
            }
                        
            for (int i = 0; i < jugadores.length; i++) {
                if (jugadores[i] != null) {
                    if (jugadores[i].obtenerPuntaje() > mayor.obtenerPuntaje()) {
                        mayor = jugadores[i];
                        indiceMayor = i;
                    }
                }
            }

            jugadores[indiceMayor] = null;
            mayorAMenor[contador] = mayor;
            contador++;
            System.out.println(contador + ". " + mayor.obtenertNickname() + ": " + mayor.obtenerPuntaje());
            tamaño--;
        }

        ordenPuntajes = mayorAMenor;
    }
    
    public void establecerGanadores(){
        int maximo = ordenPuntajes[0].obtenerPuntaje();        
        for(Jugador jugador: ordenPuntajes){
            if(jugador.obtenerPuntaje() == maximo){
                ganadores.add(jugador);
            } else {
                break;
            }
        }
    }

    @Override
    public void attach(Vista vista) {
        this.vista = vista;
    }

    @Override
    public void detach() {
        this.vista = null;
    }

    @Override
    public void notificar(Paquete paquete) {
        vista.actualizar(paquete);
    }

}
