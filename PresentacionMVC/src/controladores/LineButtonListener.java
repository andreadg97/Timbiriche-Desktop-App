package controladores;

import dominio.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import modelos.ConstantesColor;
import modelos.ModeloTablero;

public class LineButtonListener implements ActionListener {

    ModeloTablero modelo;

    public LineButtonListener(ModeloTablero modelo) {
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        Jugador jugador = modelo.obtenerPropietario();
        JButton botonLinea = (JButton) evento.getSource();
        botonLinea.setEnabled(false);
        botonLinea.setBackground(ConstantesColor.obtenerColor(jugador.obtenerColor()));
        String datos = botonLinea.getName();
        
        int fila = Integer.valueOf(datos.substring(datos.indexOf(",")+1,datos.lastIndexOf(",")));
        int columna = Integer.valueOf(datos.substring(datos.lastIndexOf(",")+1,datos.length()));
        String orientacion;
        String o = datos.substring(0, 1);

        if (o.equals("h")) { //Horizontal
            if (fila == modelo.obtenerPartida().obtenerTablero().obtenerTamañoTablero()) {
                fila -= 1;
                orientacion = "inferior";
            } else {
                orientacion = "superior";
            }
        } else { //Vertical
            if (columna == modelo.obtenerPartida().obtenerTablero().obtenerTamañoTablero()) {
                columna -= 1;
                orientacion = "derecha";
            } else {
                orientacion = "izquierda";
            }
        }

        modelo.marcarJugada(jugador, fila, columna, orientacion); 
    }

}
