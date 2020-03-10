package vistas;

import controladores.ControladorRegistro;
import dominio.Jugador;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import paquetes.Paquete;
import paquetes.PaqueteSolicitud;

public class VistaRegistro extends javax.swing.JDialog implements Vista{

    private int avatar = 0;
    private Jugador jugador;
    private JButton botonesAvatares[] = new JButton[4];
    private ControladorRegistro controlador;

    public VistaRegistro(java.awt.Frame parent, boolean modal, ControladorRegistro controlador) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.controlador = controlador;
        
        crearArregloAvatares();
        mostrarIconos();
        
        for(JButton boton: botonesAvatares){
            boton.addActionListener(controlador.crearAvatarListener());
        }
        buttonRegistrar.addActionListener(controlador.crearRegistrarseListener());
        fieldNickname.addKeyListener(controlador.crearNicknameFieldListener());
    }

    public void mostrarse(){
        this.setVisible(true);
    }
    
    @Override
    public void actualizar(Paquete paquete) {
        
        PaqueteSolicitud paqueteSolicitud = (PaqueteSolicitud) paquete;
        if(paqueteSolicitud.obtenerSolicitud().equals("confirmo conexion"))
            this.setVisible(true);
    }
    
    public void mostrarIconos() {
        avatar1.setIcon(new ImageIcon("src/vistas/Avatares/icon1_original.png"));
        avatar2.setIcon(new ImageIcon("src/vistas/Avatares/icon2_original.png"));
        avatar3.setIcon(new ImageIcon("src/vistas/Avatares/icon3_original.png"));
        avatar4.setIcon(new ImageIcon("src/vistas/Avatares/icon4_original.png"));
    }

    public void crearArregloAvatares() {
        botonesAvatares[0] = avatar1;
        botonesAvatares[1] = avatar2;
        botonesAvatares[2] = avatar3;
        botonesAvatares[3] = avatar4;
    }

    public Jugador getJugadorRegistrado() {
        return jugador;
    }

    public void marcarAvatarSeleccionado(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index - 1) {
                botonesAvatares[i].setBackground(Color.GREEN);
            } else {
                botonesAvatares[i].setBackground(null);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        avatar1 = new javax.swing.JButton();
        avatar2 = new javax.swing.JButton();
        avatar4 = new javax.swing.JButton();
        avatar3 = new javax.swing.JButton();
        buttonRegistrar = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();
        fieldNickname = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Crea tu nickname:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Elige tu avatar:");

        avatar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        avatar1.setName("1"); // NOI18N
        avatar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar1ActionPerformed(evt);
            }
        });

        avatar2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        avatar2.setName("2"); // NOI18N
        avatar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar2ActionPerformed(evt);
            }
        });

        avatar4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        avatar4.setName("4"); // NOI18N
        avatar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar4ActionPerformed(evt);
            }
        });

        avatar3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        avatar3.setName("3"); // NOI18N
        avatar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar3ActionPerformed(evt);
            }
        });

        buttonRegistrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonRegistrar.setText("Registrar");

        labelTitulo.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Registro de jugadores");

        fieldNickname.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(84, 84, 84)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(avatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(56, 56, 56))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(buttonRegistrar)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelTitulo)
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(avatar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void avatar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar1ActionPerformed
        avatar = 1;
        marcarAvatarSeleccionado(1);
    }//GEN-LAST:event_avatar1ActionPerformed

    private void avatar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar2ActionPerformed
        avatar = 2;
        marcarAvatarSeleccionado(2);
    }//GEN-LAST:event_avatar2ActionPerformed

    private void avatar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar4ActionPerformed
        avatar = 4;
        marcarAvatarSeleccionado(4);
    }//GEN-LAST:event_avatar4ActionPerformed

    private void avatar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar3ActionPerformed
        avatar = 3;
        marcarAvatarSeleccionado(3);
    }//GEN-LAST:event_avatar3ActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton avatar1;
    private javax.swing.JButton avatar2;
    private javax.swing.JButton avatar3;
    private javax.swing.JButton avatar4;
    private javax.swing.JButton buttonRegistrar;
    private javax.swing.JTextField fieldNickname;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables

    
}
