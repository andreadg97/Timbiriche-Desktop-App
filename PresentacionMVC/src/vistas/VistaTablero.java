package vistas;

import controladores.ControladorTablero;
import dominio.Cuadro;
import dominio.ElementoTablero;
import dominio.Linea;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import paquetes.Paquete;
import paquetes.PaqueteJugada;
import paquetes.PaqueteNuevoJugador;
import paquetes.PaqueteTablero;
import modelos.ConstantesColor;
import paquetes.PaqueteElementos;
import paquetes.PaqueteEstado;

/**
 * Esta clase permite mostrar gráficamente el estado del tablero de determinado
 * jugador e interactuar con él y los demás jugadores.
 *
 * @author Grandío, Díaz, Flores
 */
public class VistaTablero extends javax.swing.JFrame implements Vista {

    private ControladorTablero control;
    private JButton[][] lineasVerticales;
    private JButton[][] lineasHorizontales;
    private JLabel[][] cuadros;
    private int tamañoTablero;

    public VistaTablero(ControladorTablero control) {
        initComponents();
        this.control = control;
        this.setLocationRelativeTo(null);
        labelPartida.setFocusable(false);
        botonActualizarColores.addActionListener(control.crearColorButtonListener());
        botonAbandonar.addActionListener(control.crearAbandonarButtonListener());

        for (JComboBox comboBox : obtenerComboBoxes()) {
            comboBox.addItem("    ");
            comboBox.setSelectedIndex(0);
        }
        this.setResizable(true);
    }

    public JComboBox[] obtenerComboBoxes() {
        JComboBox[] comboBoxes = new JComboBox[4];
        comboBoxes[0] = comboBox1;
        comboBoxes[1] = comboBox2;
        comboBoxes[2] = comboBox3;
        comboBoxes[3] = comboBox4;

        for (JComboBox cb : comboBoxes) {
            cb.addActionListener(control.crearComboBoxListener());
        }
        return comboBoxes;
    }

    public JLabel[] obtenerLabelsAvatares() {
        JLabel labels[] = {avatar1, avatar2, avatar3, avatar4};
        return labels;
    }

    public JLabel[] obtenerLabelsNickname() {
        JLabel labels[] = {nickname1, nickname2, nickname3, nickname4};
        return labels;
    }

    public JLabel[] obtenerLabelsPuntajes() {
        JLabel labels[] = {puntaje1, puntaje2, puntaje3, puntaje4};
        return labels;
    }

    public JLabel[] obtenerLabelsTitulosNicknames() {
        JLabel labels[] = {labelNN1, labelNN2, labelNN3, labelNN4};
        return labels;
    }

    public JLabel[] obtenerLabelsTitulosPuntajes() {
        JLabel labels[] = {labelP1, labelP2, labelP3, labelP4};
        return labels;
    }

    public void habilitarTablero() {
        for (int i = 0; i <= tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                if (lineasHorizontales[i][j].getBackground() == Color.WHITE) {
                    lineasHorizontales[i][j].setEnabled(true);
                }
            }
        }

        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j <= tamañoTablero; j++) {
                if (lineasVerticales[i][j].getBackground() == Color.WHITE) {
                    lineasVerticales[i][j].setEnabled(true);
                }
            }
        }
    }

    public void deshabilitarTablero() {
        for (int i = 0; i < tamañoTablero; i++) {
            for (int j = 0; j <= tamañoTablero; j++) {
                lineasVerticales[i][j].setEnabled(false);
            }
        }

        for (int i = 0; i <= tamañoTablero; i++) {
            for (int j = 0; j < tamañoTablero; j++) {
                lineasHorizontales[i][j].setEnabled(false);
            }
        }
    }

    public void mostrar() {
        this.setVisible(true);
    }

    public void crearLinea(String tipo, Point tamaño, Point ubicacion, Point coordenada) {
        JButton linea = new JButton();
        linea.addActionListener(control.crearButtonListener());
        linea.setName(tipo.substring(0, 1) + "," + coordenada.x + "," + coordenada.y);
        linea.setSize(tamaño.x, tamaño.y);
        linea.setLocation(ubicacion.y, ubicacion.x);
        linea.setVisible(true);
        linea.setBackground(Color.WHITE);
        paneTablero.add(linea);

        if (tipo.equals("horizontal")) {
            lineasHorizontales[coordenada.x][coordenada.y] = linea;
        } else {
            lineasVerticales[coordenada.x][coordenada.y] = linea;
        }
    }

    public void crearCuadro(Point tamaño, Point ubicacion, Point coordenada) {
        JLabel cuadro = new JLabel();
        cuadro.setName(coordenada.x + "," + coordenada.y);
        cuadro.setVisible(true);
        cuadro.setOpaque(true);
        cuadro.setSize(tamaño.x, tamaño.y);
        cuadro.setBackground(Color.WHITE);
        cuadro.setLocation(ubicacion.y, ubicacion.x);
        paneTablero.add(cuadro);
        paneTablero.revalidate();
        this.validate();
        cuadros[coordenada.x][coordenada.y] = cuadro;
    }

    @Override
    public void actualizar(Paquete paquete) {
        if (paquete instanceof PaqueteTablero) {
            PaqueteTablero pt = (PaqueteTablero) paquete;
            actualizarTablero(pt);
        }

        if (paquete instanceof PaqueteJugada) {
            PaqueteJugada pj = (PaqueteJugada) paquete;
            actualizarJugada(pj);
        }

        if (paquete instanceof PaqueteNuevoJugador) {
            PaqueteNuevoJugador pnj = (PaqueteNuevoJugador) paquete;
            actualizarNuevoJugador(pnj);
        }

        if (paquete instanceof PaqueteElementos) {
            PaqueteElementos pe = (PaqueteElementos) paquete;
            if (pe.obtenerAccion().equals("cambio")) {
                actualizarColorJugador(pe);
            } else {
                if (pe.obtenerAccion().equals("seleccion")) {
                    actualizarColorSeleccionado(pe);
                } else {
                    actualizarAbandonoJugador(pe);
                }
            }
        }

        if (paquete instanceof PaqueteEstado) {
            PaqueteEstado paqEstado = (PaqueteEstado) paquete;
            actualizarEstado(paqEstado);
        }
    }

    public void actualizarColorSeleccionado(PaqueteElementos paquete) {
        for (JComboBox comboBox : obtenerComboBoxes()) {
            if (comboBox.getName().equals(paquete.obtenerJugador().obtenerColor())) {
                comboBox.setSelectedItem(paquete.obtenerJugador().obtenertNickname());
            } else {
                String seleccionado = (String) comboBox.getSelectedItem();
                if (seleccionado.equals(paquete.obtenerJugador().obtenertNickname())) {
                    comboBox.setSelectedIndex(0);
                }
            }
        }
    }

    public void actualizarEstado(PaqueteEstado paquete) {
        labelPartida.setText(paquete.obtenerEstado());

        if (paquete.obtenerJugador() == null) {
            this.dispose();
        } else {
            botonIniciarPartida.setEnabled(false);
            labelJugadorTurno.setText(paquete.obtenerJugador().obtenertNickname());

            if (paquete.obtenerJugador().obtenertNickname().equals(nickname1.getText())) {
                labelJugadorTurno.setBackground(Color.GREEN);
                habilitarTablero();
            } else {
                deshabilitarTablero();
                labelJugadorTurno.setBackground(Color.WHITE);
            }
        }
    }

    public void actualizarColorJugador(PaqueteElementos pe) {
        ArrayList<ElementoTablero> elementos = pe.obtenerElementosJugador();
        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Linea) {
                Linea linea = (Linea) elemento;
                int fila = linea.obtenerCoordenada()[0];
                int columna = linea.obtenerCoordenada()[1];
                Color color = ConstantesColor.obtenerColor(pe.obtenerJugador().obtenerColor());

                if (linea.obtenerTamaño()[0] > linea.obtenerTamaño()[1]) {
                    lineasHorizontales[fila][columna].setBackground(color);
                } else {
                    lineasVerticales[fila][columna].setBackground(color);
                }
            } else {
                Cuadro cuadro = (Cuadro) elemento;
                Color color = ConstantesColor.obtenerColor(pe.obtenerJugador().obtenerColor());
                int fila = cuadro.obtenerFilaCuadricula();
                int columna = cuadro.obtenerColumnaCuadricula();
                cuadros[fila][columna].setBackground(color);
            }
        }
    }

    public void actualizarAbandonoJugador(PaqueteElementos pe) {
        ArrayList<ElementoTablero> elementos = pe.obtenerElementosJugador();

        for (ElementoTablero elemento : elementos) {
            if (elemento instanceof Linea) {
                Linea linea = (Linea) elemento;
                int fila = linea.obtenerCoordenada()[0];
                int columna = linea.obtenerCoordenada()[1];

                if (linea.obtenerTamaño()[0] > linea.obtenerTamaño()[1]) {
                    lineasHorizontales[fila][columna].setBackground(Color.BLACK);
                } else {
                    lineasVerticales[fila][columna].setBackground(Color.BLACK);
                }
            } else {
                Cuadro cuadro = (Cuadro) elemento;
                int fila = cuadro.obtenerFilaCuadricula();
                int columna = cuadro.obtenerColumnaCuadricula();
                cuadros[fila][columna].setBackground(Color.BLACK);
            }
        }

        int indice = -1;
        for (JLabel label : obtenerLabelsNickname()) {
            indice++;
            if (label.getText().equals(pe.obtenerJugador().obtenertNickname())) {
                label.setText("");
                break;
            }
        }

        obtenerLabelsAvatares()[indice].setIcon(null);
        obtenerLabelsPuntajes()[indice].setText(null);

        for (JComboBox cb : obtenerComboBoxes()) {
            cb.removeItem(pe.obtenerJugador().obtenertNickname());
            if (cb.getName().equals(pe.obtenerJugador().obtenerColor())) {
                cb.setSelectedIndex(0);
            }
        }
    }

    public void actualizarNuevoJugador(PaqueteNuevoJugador pnj) {
        int indice = -1;
        for (JLabel nickname : obtenerLabelsNickname()) {
            indice++;
            if (nickname.getText().isEmpty()) {
                nickname.setText(pnj.obtenerJugador().obtenertNickname());
                break;
            }
        }
        obtenerLabelsPuntajes()[indice].setText(String.valueOf(pnj.obtenerJugador().obtenerPuntaje()));
        ImageIcon avatar = new ImageIcon("src/vistas/IconoAvatares/icon" + pnj.obtenerJugador().obtenerAvatar() + ".png");
        obtenerLabelsAvatares()[indice].setIcon(avatar);

        for (JComboBox comboBox : obtenerComboBoxes()) {
            comboBox.addItem(pnj.obtenerJugador().obtenertNickname());
        }
        for (JComboBox comboBox : obtenerComboBoxes()) {
            if (comboBox.getName().equals(pnj.obtenerJugador().obtenerColor())) {
                comboBox.setSelectedItem(pnj.obtenerJugador().obtenertNickname());
            }
        }
    }

    public void actualizarJugada(PaqueteJugada pj) {
        labelPartida.setText(pj.obtenerEstadoPartida());

        int indice = -1;
        for (JLabel nickname : obtenerLabelsNickname()) {
            indice++;
            if (nickname.getText().equals(pj.obtenerJugador().obtenertNickname())) {
                break;
            }
        }

        obtenerLabelsPuntajes()[indice].setText(String.valueOf(pj.obtenerJugador().obtenerPuntaje()));

        for (ElementoTablero elemento : pj.obtenerElementosJugada()) {
            if (elemento instanceof Cuadro) {
                Cuadro cuadro = (Cuadro) elemento;
                int x = cuadro.obtenerFilaCuadricula();
                int y = cuadro.obtenerColumnaCuadricula();

                Color color = ConstantesColor.obtenerColor(pj.obtenerJugador().obtenerColor());
                cuadros[x][y].setBackground(color);
            }
            if (elemento instanceof Linea) {
                Linea linea = (Linea) elemento;
                int fila = linea.obtenerCoordenada()[0];
                int columna = linea.obtenerCoordenada()[1];

                if (linea.obtenerTamaño()[0] > linea.obtenerTamaño()[1]) { //Horizontal    
                    lineasHorizontales[fila][columna].setBackground(ConstantesColor.obtenerColor(pj.obtenerJugador().obtenerColor()));
                } else { //Vertical
                    lineasVerticales[fila][columna].setBackground(ConstantesColor.obtenerColor(pj.obtenerJugador().obtenerColor()));
                }
            }
        }
    }

    public void actualizarTablero(PaqueteTablero pt) {
        mostrar();
        ArrayList<Object[]> listaLineas = pt.obtenerLineas();
        ArrayList<Object[]> listaCuadros = pt.obtenerCuadros();

        int tamañoTablero = pt.obtenerTamanioTablero();
        this.tamañoTablero = tamañoTablero;
        lineasHorizontales = new JButton[tamañoTablero + 1][tamañoTablero];
        lineasVerticales = new JButton[tamañoTablero][tamañoTablero + 1];
        cuadros = new JLabel[tamañoTablero][tamañoTablero];

        for (Object[] datos : listaLineas) {
            Linea linea = (Linea) datos[0];
            Point ubicacion = (Point) datos[1];
            String tipo = (String) datos[2];
            Point coordenada = (Point) datos[3];
            Point tamaño = new Point(linea.obtenerTamaño()[0], linea.obtenerTamaño()[1]);

            crearLinea(tipo, tamaño, ubicacion, coordenada);
        }

        for (Object[] datos : listaCuadros) {
            Cuadro cuadro = (Cuadro) datos[0];
            Point tamaño = new Point(cuadro.obtenerTamaño()[0], cuadro.obtenerTamaño()[1]);
            Point ubicacion = (Point) datos[1];
            Point coordenada = new Point(cuadro.obtenerFilaCuadricula(), cuadro.obtenerColumnaCuadricula());

            crearCuadro(tamaño, ubicacion, coordenada);
        }

        this.pack();

        nickname1.setText(pt.obtenerJugador().obtenertNickname());
        puntaje1.setText(String.valueOf(pt.obtenerJugador().obtenerPuntaje()));
        labelPartida.setText("Pendiente");
        labelJugadorTurno.setText("No aplica");
        ImageIcon avatar = new ImageIcon("src/vistas/IconoAvatares/icon" + pt.obtenerJugador().obtenerAvatar() + ".png");
        avatar1.setIcon(avatar);

        for (JComboBox comboBox : obtenerComboBoxes()) {
            comboBox.addItem(pt.obtenerJugador().obtenertNickname());
        }
        for (JComboBox comboBox : obtenerComboBoxes()) {
            if (comboBox.getName().equals(pt.obtenerJugador().obtenerColor())) {
                comboBox.setSelectedItem(pt.obtenerJugador().obtenertNickname());
            }
        }

        botonIniciarPartida.addActionListener(control.crearIniciarButtonListener());
        deshabilitarTablero();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPersonal = new javax.swing.JPanel();
        avatar1 = new javax.swing.JLabel();
        labelNN1 = new javax.swing.JLabel();
        labelP1 = new javax.swing.JLabel();
        nickname1 = new javax.swing.JLabel();
        puntaje1 = new javax.swing.JLabel();
        panelContrincantes = new javax.swing.JPanel();
        nickname2 = new javax.swing.JLabel();
        labelNN2 = new javax.swing.JLabel();
        puntaje2 = new javax.swing.JLabel();
        labelP2 = new javax.swing.JLabel();
        avatar2 = new javax.swing.JLabel();
        nickname3 = new javax.swing.JLabel();
        labelNN3 = new javax.swing.JLabel();
        puntaje3 = new javax.swing.JLabel();
        labelP3 = new javax.swing.JLabel();
        avatar3 = new javax.swing.JLabel();
        labelNN4 = new javax.swing.JLabel();
        nickname4 = new javax.swing.JLabel();
        avatar4 = new javax.swing.JLabel();
        labelP4 = new javax.swing.JLabel();
        puntaje4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelJugadorTurno = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        panelConfiguracion = new javax.swing.JPanel();
        labelNickname1 = new javax.swing.JLabel();
        comboBox1 = new javax.swing.JComboBox<>();
        comboBox2 = new javax.swing.JComboBox<>();
        comboBox3 = new javax.swing.JComboBox<>();
        comboBox4 = new javax.swing.JComboBox<>();
        labelNickname2 = new javax.swing.JLabel();
        labelNickname3 = new javax.swing.JLabel();
        labelNickname4 = new javax.swing.JLabel();
        labelNickname5 = new javax.swing.JLabel();
        botonActualizarColores = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        labelPartida = new javax.swing.JTextField();
        paneTablero = new javax.swing.JPanel();
        botonIniciarPartida = new javax.swing.JButton();
        botonAbandonar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1050, 800));

        panelPersonal.setBackground(new java.awt.Color(255, 255, 255));
        panelPersonal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelNN1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNN1.setText("Nickname:");

        labelP1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelP1.setText("Puntaje:");

        javax.swing.GroupLayout panelPersonalLayout = new javax.swing.GroupLayout(panelPersonal);
        panelPersonal.setLayout(panelPersonalLayout);
        panelPersonalLayout.setHorizontalGroup(
            panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNN1, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nickname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(puntaje1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelPersonalLayout.setVerticalGroup(
            panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPersonalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPersonalLayout.createSequentialGroup()
                        .addGroup(panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelNN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nickname1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(puntaje1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        panelContrincantes.setBackground(new java.awt.Color(255, 255, 255));
        panelContrincantes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panelContrincantes.setPreferredSize(new java.awt.Dimension(0, 190));

        labelNN2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNN2.setText("Nickname:");

        labelP2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelP2.setText("Puntaje:");

        labelNN3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNN3.setText("Nickname:");

        labelP3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelP3.setText("Puntaje:");

        labelNN4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelNN4.setText("Nickname:");

        labelP4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelP4.setText("Puntaje:");

        javax.swing.GroupLayout panelContrincantesLayout = new javax.swing.GroupLayout(panelContrincantes);
        panelContrincantes.setLayout(panelContrincantesLayout);
        panelContrincantesLayout.setHorizontalGroup(
            panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContrincantesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNN4, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(labelP4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addComponent(avatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelNN2, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(labelP2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNN3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelP3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nickname2, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(puntaje2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nickname3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(puntaje3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nickname4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(puntaje4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        panelContrincantesLayout.setVerticalGroup(
            panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContrincantesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addComponent(labelNN2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelP2))
                    .addComponent(avatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addComponent(nickname2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(puntaje2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelContrincantesLayout.createSequentialGroup()
                        .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNN3)
                            .addComponent(nickname3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(puntaje3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelP3))))
                .addGap(18, 18, 18)
                .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelContrincantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panelContrincantesLayout.createSequentialGroup()
                            .addComponent(nickname4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(puntaje4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelContrincantesLayout.createSequentialGroup()
                            .addComponent(labelNN4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(labelP4))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Mis datos");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Contrincantes");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Configuración de colores");

        labelJugadorTurno.setBackground(new java.awt.Color(255, 255, 255));
        labelJugadorTurno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelJugadorTurno.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        labelJugadorTurno.setOpaque(true);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Turno");

        panelConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        panelConfiguracion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panelConfiguracion.setMaximumSize(new java.awt.Dimension(300, 160));
        panelConfiguracion.setPreferredSize(new java.awt.Dimension(300, 160));

        labelNickname1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelNickname1.setMaximumSize(new java.awt.Dimension(210, 20));

        comboBox1.setMaximumSize(new java.awt.Dimension(62, 20));
        comboBox1.setName("rojo"); // NOI18N

        comboBox2.setName("amarillo"); // NOI18N

        comboBox3.setName("azul"); // NOI18N

        comboBox4.setName("verde"); // NOI18N

        labelNickname2.setBackground(new java.awt.Color(255, 204, 51));
        labelNickname2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelNickname2.setMaximumSize(new java.awt.Dimension(210, 20));
        labelNickname2.setOpaque(true);

        labelNickname3.setBackground(new java.awt.Color(204, 0, 0));
        labelNickname3.setForeground(new java.awt.Color(204, 0, 0));
        labelNickname3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelNickname3.setMaximumSize(new java.awt.Dimension(210, 20));
        labelNickname3.setOpaque(true);

        labelNickname4.setBackground(new java.awt.Color(0, 0, 153));
        labelNickname4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelNickname4.setMaximumSize(new java.awt.Dimension(210, 20));
        labelNickname4.setOpaque(true);

        labelNickname5.setBackground(new java.awt.Color(0, 204, 0));
        labelNickname5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelNickname5.setMaximumSize(new java.awt.Dimension(210, 20));
        labelNickname5.setOpaque(true);

        javax.swing.GroupLayout panelConfiguracionLayout = new javax.swing.GroupLayout(panelConfiguracion);
        panelConfiguracion.setLayout(panelConfiguracionLayout);
        panelConfiguracionLayout.setHorizontalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNickname1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelConfiguracionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboBox3, javax.swing.GroupLayout.Alignment.LEADING, 0, 229, Short.MAX_VALUE)))
                    .addGroup(panelConfiguracionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(comboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        panelConfiguracionLayout.setVerticalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNickname1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelNickname4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(comboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        botonActualizarColores.setText("actualizar");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Partida");

        labelPartida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        labelPartida.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        paneTablero.setPreferredSize(new java.awt.Dimension(650, 650));

        javax.swing.GroupLayout paneTableroLayout = new javax.swing.GroupLayout(paneTablero);
        paneTablero.setLayout(paneTableroLayout);
        paneTableroLayout.setHorizontalGroup(
            paneTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        paneTableroLayout.setVerticalGroup(
            paneTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        botonIniciarPartida.setText("INICIAR PARTIDA");

        botonAbandonar.setText("abandonar partida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paneTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonActualizarColores))
                        .addComponent(panelPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonIniciarPartida))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(labelJugadorTurno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botonAbandonar))
                                    .addComponent(panelContrincantes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(labelPartida, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paneTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonIniciarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(3, 3, 3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelContrincantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(botonAbandonar, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelJugadorTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelPartida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonActualizarColores, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addComponent(panelConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatar1;
    private javax.swing.JLabel avatar2;
    private javax.swing.JLabel avatar3;
    private javax.swing.JLabel avatar4;
    private javax.swing.JButton botonAbandonar;
    private javax.swing.JButton botonActualizarColores;
    private javax.swing.JButton botonIniciarPartida;
    private javax.swing.JComboBox<String> comboBox1;
    private javax.swing.JComboBox<String> comboBox2;
    private javax.swing.JComboBox<String> comboBox3;
    private javax.swing.JComboBox<String> comboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labelJugadorTurno;
    private javax.swing.JLabel labelNN1;
    private javax.swing.JLabel labelNN2;
    private javax.swing.JLabel labelNN3;
    private javax.swing.JLabel labelNN4;
    private javax.swing.JLabel labelNickname1;
    private javax.swing.JLabel labelNickname2;
    private javax.swing.JLabel labelNickname3;
    private javax.swing.JLabel labelNickname4;
    private javax.swing.JLabel labelNickname5;
    private javax.swing.JLabel labelP1;
    private javax.swing.JLabel labelP2;
    private javax.swing.JLabel labelP3;
    private javax.swing.JLabel labelP4;
    private javax.swing.JTextField labelPartida;
    private javax.swing.JLabel nickname1;
    private javax.swing.JLabel nickname2;
    private javax.swing.JLabel nickname3;
    private javax.swing.JLabel nickname4;
    private javax.swing.JPanel paneTablero;
    private javax.swing.JPanel panelConfiguracion;
    private javax.swing.JPanel panelContrincantes;
    private javax.swing.JPanel panelPersonal;
    private javax.swing.JLabel puntaje1;
    private javax.swing.JLabel puntaje2;
    private javax.swing.JLabel puntaje3;
    private javax.swing.JLabel puntaje4;
    // End of variables declaration//GEN-END:variables
}
