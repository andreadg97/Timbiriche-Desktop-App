package controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelos.ModeloTablero;

public class ColorButtonListener implements ActionListener{

    ModeloTablero modelo;
    
    public ColorButtonListener(ModeloTablero modelo){
        this.modelo = modelo;
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        
        JButton boton = (JButton) evento.getSource();
        boton.setSelected(false);
        
        boolean todosTienenColorAsignado = modelo.todosTienenColorAsignado();
        if(todosTienenColorAsignado){
            modelo.actualizarColorJugadores();
        } else {
            JOptionPane.showMessageDialog(null, null, "Todos los jugadores deben tener color", 0);
        }
    }
    
}