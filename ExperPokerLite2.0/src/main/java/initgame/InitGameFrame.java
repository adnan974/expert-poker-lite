/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package initgame;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.ImageIcon;
import modele.Dealer;
import modele.Joueur;
//import tablejeu.TableJeuFenetre;
import tablejeu.TableJeuFrame;

/**
 *
 * @author borri
 */
public class InitGameFrame extends javax.swing.JFrame {

    Dealer dealer;
    Socket me;

    /**
     * Creates new form InitGameFrame
     */
    public InitGameFrame() {
        initComponents();
        dealer = new Dealer();

        ImageIcon icone = new ImageIcon("img/experpokerlite.png");

        icone.setImage(icone.getImage().getScaledInstance(jlbl_titre.getWidth(), jlbl_titre.getHeight(), Image.SCALE_SMOOTH));
        jlbl_titre.setIcon(icone);
        jlbl_titre.repaint();

        Font f1 = null;
        try {
            File fis = new File("font/bankgothic.ttf");
            f1 = Font.createFont(Font.TRUETYPE_FONT, fis);
            f1 = f1.deriveFont((float) 15.0);

        } catch (Exception e) {
        }
        
                Font f2 = null;
        try {
            File fis = new File("font/bankgothicbold.ttf");
            f2 = Font.createFont(Font.TRUETYPE_FONT, fis);
            f2 = f2.deriveFont((float) 15.0);

        } catch (Exception e) {
        }
        btn_play.setFont(f2);
        jLabel1.setFont(f2);
        txt_playerName.setFont(f1);  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_playerName = new javax.swing.JTextField();
        btn_play = new javax.swing.JButton();
        jlbl_titre = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nom  Joueur :");

        txt_playerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_playerNameActionPerformed(evt);
            }
        });

        btn_play.setText("Jouer  !");
        btn_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_playActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txt_playerName)
                .addGap(67, 67, 67))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jlbl_titre, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jlbl_titre, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_playerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_play)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_playerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_playerNameActionPerformed
        // TODO add your handling code here:
        btn_playActionPerformed(null);
    }//GEN-LAST:event_txt_playerNameActionPerformed

    private void btn_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_playActionPerformed

        try {
            me = new Socket("127.0.0.1", 4444);
        } catch (IOException ex) {
        }

        String playerName = txt_playerName.getText();
        Joueur joueur = new Joueur(playerName);

        try {

            ObjectOutputStream oos = new ObjectOutputStream(me.getOutputStream());
            oos.writeObject(joueur);
            oos.flush();
        } catch (IOException ex) {

        }
        TableJeuFrame tj = new TableJeuFrame(me, joueur);
        tj.setVisible(true);
        this.setVisible(false);


    }//GEN-LAST:event_btn_playActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitGameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitGameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitGameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitGameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InitGameFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_play;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlbl_titre;
    private javax.swing.JTextField txt_playerName;
    // End of variables declaration//GEN-END:variables
}
