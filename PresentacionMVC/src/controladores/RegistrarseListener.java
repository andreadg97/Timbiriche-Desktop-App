package controladores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelos.ModeloRegistro;
public class RegistrarseListener implements ActionListener{
    
    private ModeloRegistro modelo;
    
    public RegistrarseListener(ModeloRegistro modelo){
        this.modelo = modelo;
    }    

    @Override
    public void actionPerformed(ActionEvent evento) {  
        JButton boton = (JButton) evento.getSource();
        boton.setSelected(false);

        boolean nicknameValido = modelo.validarNickname();
        boolean avatarValido = modelo.validarAvatar();
        
        if(nicknameValido == false){
            JOptionPane.showMessageDialog(null, null, "Elige un nickname menor a 20 caraceteres", 0);
        } else {
            if(avatarValido == false){
                JOptionPane.showMessageDialog(null, null, "Elege un avatar", 0);
            } else {
                modelo.registrarJugador();
            }
        }
    }    
    
}