package com.vistas;

import com.entities.Rol;
import com.utilidades.ValidarAccesos;

/**
 * Nombre de la clase: FrmPrincipal Fecha: 1/11/2020 CopyRigth: Pedro Campos
 * Modificacion: 06/11/2020 Version: 1.1
 *
 * @author pedro
 */
public class FrmPrincipal extends javax.swing.JFrame {

    static public String nivel;
    private FrmProyecto formulario = null;
    private FrmUsuario usuario = null;
    private FrmUbicacion ubicacion = null;

    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        setResizable(true);
        setLocationRelativeTo(null);
        setExtendedState(FrmPrincipal.MAXIMIZED_BOTH);
        nivelAcceso();
    }

    public void nivelAcceso() {
        ValidarAccesos obj = new ValidarAccesos();

        try {

            switch (obj.getAcceso().getTipoNivel()) {
                case "Administrador":
                    break;
                case "Empleado":
                    //bloquea la accion de los item de menu
                    usuarioItem.setEnabled(false);
                    break;
                case "Invitado":
                    //bloquea la accion de los item de menu
                    ubicacionItem.setEnabled(false);
                    usuarioItem.setEnabled(false);
                    ubicacionItem.setEnabled(false);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        }
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dskPrincipal = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        ubicacionItem = new javax.swing.JMenuItem();
        usuarioItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout dskPrincipalLayout = new javax.swing.GroupLayout(dskPrincipal);
        dskPrincipal.setLayout(dskPrincipalLayout);
        dskPrincipalLayout.setHorizontalGroup(
            dskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );
        dskPrincipalLayout.setVerticalGroup(
            dskPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("Ventanas");

        ubicacionItem.setText("Proyectos");
        ubicacionItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubicacionItemActionPerformed(evt);
            }
        });
        fileMenu.add(ubicacionItem);

        usuarioItem.setText("Usuarios");
        usuarioItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioItemActionPerformed(evt);
            }
        });
        fileMenu.add(usuarioItem);

        jMenuItem1.setText("Ubicaciones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem1);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Opciones");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dskPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ubicacionItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubicacionItemActionPerformed
        if (formulario == null || formulario.isClosed()) {
            formulario = new FrmProyecto();
            this.dskPrincipal.add(formulario);
        }
        formulario.setVisible(true);
    }//GEN-LAST:event_ubicacionItemActionPerformed

    private void usuarioItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioItemActionPerformed
        if (usuario == null || usuario.isClosed()) {
            usuario = new FrmUsuario();
            this.dskPrincipal.add(usuario);
        }
        usuario.setVisible(true);
    }//GEN-LAST:event_usuarioItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(ubicacion == null || ubicacion.isClosed()){
            ubicacion = new FrmUbicacion();
            this.dskPrincipal.add(ubicacion);
        }
        ubicacion.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JDesktopPane dskPrincipal;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem ubicacionItem;
    private javax.swing.JMenuItem usuarioItem;
    // End of variables declaration//GEN-END:variables

}
