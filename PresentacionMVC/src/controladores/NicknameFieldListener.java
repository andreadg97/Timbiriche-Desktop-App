package controladores;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import modelos.ModeloRegistro;

public class NicknameFieldListener extends KeyAdapter{
    
    private ModeloRegistro modelo;
    
    public NicknameFieldListener(ModeloRegistro modelo){
        this.modelo = modelo;
    }
    
    @Override
    public void keyReleased(KeyEvent evento) {
        JTextField fieldNickname = (JTextField) evento.getSource();
        
        modelo.establecerNickname(fieldNickname.getText());
    }
    
}