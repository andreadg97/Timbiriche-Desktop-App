package controladores;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import modelos.ModeloRegistro;

public class AvatarListener implements ActionListener{
    
    private ModeloRegistro modelo;
    
    public AvatarListener(ModeloRegistro modelo){
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        
        JButton boton = (JButton) evento.getSource();
        boton.setSelected(true);
        boton.setBackground(Color.GREEN);
        
        modelo.establecerAvatar(Integer.valueOf(boton.getName()));
        
    }
    
}