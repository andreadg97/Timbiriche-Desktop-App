package controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import modelos.ModeloTablero;

public class IniciarButtonListener implements ActionListener{

    private ModeloTablero modelo;
    
    public IniciarButtonListener(ModeloTablero modelo){
        this.modelo = modelo;
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        
        JButton boton = (JButton) evento.getSource();
        boton.setEnabled(false);
        
        modelo.notificarJugadorListo(modelo.obtenerPropietario());
        
    }
    
}