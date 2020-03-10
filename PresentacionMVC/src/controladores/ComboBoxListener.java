package controladores;

import dominio.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import modelos.ModeloTablero;

public class ComboBoxListener implements ActionListener {

    private ModeloTablero modelo;

    public ComboBoxListener(ModeloTablero modelo) {
        this.modelo = modelo;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        JComboBox comboBox = (JComboBox) evento.getSource();
        String color = comboBox.getName();

            String seleccionado = (String) comboBox.getSelectedItem();

            Jugador jugador = modelo.obtenerPartida().obtenerTurno().obtenerJugador(seleccionado);
            if (jugador != null) {
                modelo.establecerColorProvisional(jugador, color);
            } else {
                modelo.establecerColorProvisional(null, color);
            }

    }

}
