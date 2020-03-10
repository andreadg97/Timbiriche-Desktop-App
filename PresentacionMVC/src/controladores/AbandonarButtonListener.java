package controladores;
import dominio.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelos.ModeloTablero;

public class AbandonarButtonListener implements ActionListener{

    private ModeloTablero modelo;
    
    public AbandonarButtonListener(ModeloTablero modelo){
        this.modelo = modelo;
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        
        Jugador jugador = modelo.obtenerPropietario();
        
        modelo.abandonarPartida(jugador);
        modelo.detach();
    }
    
}