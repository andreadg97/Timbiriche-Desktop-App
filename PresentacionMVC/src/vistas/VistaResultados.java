package vistas;
import dominio.Jugador;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import paquetes.Paquete;
import paquetes.PaqueteResultados;

public class VistaResultados extends javax.swing.JFrame implements Vista{

    private JLabel labelJugadores[] = new JLabel[4];
    private JLabel labelGanadores[] = new JLabel[4];
    private JLabel labelNicknames[] = new JLabel[4];
    private JLabel labelPuntajes[] = new JLabel[4];
    
    public VistaResultados() {
        initComponents();
        
        labelJugadores[0] = label1;
        labelJugadores[1] = label2;
        labelJugadores[2] = label3;
        labelJugadores[3] = label4;
        
        labelGanadores[0] = labelGanador1;
        labelGanadores[1] = labelGanador2;
        labelGanadores[2] = labelGanador3;
        labelGanadores[3] = labelGanador4;
        
        labelNicknames[0] = labelNickname1;
        labelNicknames[1] = labelNickname2;
        labelNicknames[2] = labelNickname3;
        labelNicknames[3] = labelNickname4;
        
        labelPuntajes[0] = labelPuntos1;
        labelPuntajes[1] = labelPuntos2;
        labelPuntajes[2] = labelPuntos3;
        labelPuntajes[3] = labelPuntos4;
    }
    
    public JLabel[] obtenerLabelJugadores(){
        return labelJugadores;
    }
    
    public JLabel[] obtenerLabelGanadores(){
        return labelGanadores;
    }
    
    public JLabel[] obtenerLabelNicknames(){
        return labelNicknames;
    }
    
    public JLabel[] obtenerLabelPuntajes(){
        return labelPuntajes;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        labelGanador1 = new javax.swing.JLabel();
        labelGanador2 = new javax.swing.JLabel();
        labelGanador3 = new javax.swing.JLabel();
        labelGanador4 = new javax.swing.JLabel();
        labelNickname1 = new javax.swing.JLabel();
        labelNickname2 = new javax.swing.JLabel();
        labelNickname3 = new javax.swing.JLabel();
        labelNickname4 = new javax.swing.JLabel();
        labelPuntos1 = new javax.swing.JLabel();
        labelPuntos2 = new javax.swing.JLabel();
        labelPuntos3 = new javax.swing.JLabel();
        labelPuntos4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Resultados");

        label1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        label1.setPreferredSize(new java.awt.Dimension(17, 17));

        label4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        label4.setPreferredSize(new java.awt.Dimension(17, 17));

        label2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        label2.setPreferredSize(new java.awt.Dimension(17, 17));

        label3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        label3.setPreferredSize(new java.awt.Dimension(17, 17));

        labelGanador1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelGanador2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelGanador3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelGanador4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelNickname1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNickname1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelNickname2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNickname2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelNickname3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNickname3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelNickname4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNickname4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelPuntos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPuntos1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelPuntos2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPuntos2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelPuntos3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPuntos3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        labelPuntos4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPuntos4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(labelGanador1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(labelGanador2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(labelGanador3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelGanador4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelNickname1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(labelNickname2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNickname3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelNickname4, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(labelPuntos1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(labelPuntos2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(labelPuntos3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelPuntos4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelGanador1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGanador2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGanador3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelGanador4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNickname1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNickname4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPuntos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPuntos2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(labelPuntos3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(labelPuntos4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel labelGanador1;
    private javax.swing.JLabel labelGanador2;
    private javax.swing.JLabel labelGanador3;
    private javax.swing.JLabel labelGanador4;
    private javax.swing.JLabel labelNickname1;
    private javax.swing.JLabel labelNickname2;
    private javax.swing.JLabel labelNickname3;
    private javax.swing.JLabel labelNickname4;
    private javax.swing.JLabel labelPuntos1;
    private javax.swing.JLabel labelPuntos2;
    private javax.swing.JLabel labelPuntos3;
    private javax.swing.JLabel labelPuntos4;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actualizar(Paquete paquete) {
        PaqueteResultados paqueteResultados = (PaqueteResultados) paquete;
        Jugador jugadores[] = paqueteResultados.obtenerJugadores();
        int numeroJugadores =  jugadores.length;
        int contador = 0;
        
        for(Jugador jugador: jugadores){
            ImageIcon avatar = new ImageIcon("src/vistas/Avatares/icon"+jugador.obtenerAvatar()+"_original.png");
            obtenerLabelJugadores()[contador].setIcon(avatar);
            obtenerLabelNicknames()[contador].setText(jugador.obtenertNickname());
            obtenerLabelPuntajes()[contador].setText(String.valueOf(jugador.obtenerPuntaje())+" puntos");
            contador++;
        }
        
        contador = 0;
        int ganadores = paqueteResultados.obtenerGanadores().length;
        for(Jugador jugador: paqueteResultados.obtenerGanadores()){
            labelGanadores[contador].setIcon(new ImageIcon("src/vistas/Avatares/corona.png"));
            contador++;            
        }
        
        this.setVisible(true);
    }
}
