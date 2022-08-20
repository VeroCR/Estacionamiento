/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dpo3_u2_a2_verm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vero_
 */
public class ReporteCajones extends javax.swing.JFrame {

     //Llamar a la clase conexión para la base de datos
        Conexion con = new Conexion();
        Connection reg = con.conectarMySQL();
        Statement stmt;
        ResultSet rs;
        
    public ReporteCajones() {
        initComponents();
        setLocationRelativeTo(null);
        ReporteCajon rep = new ReporteCajon();
        rep.start();
    }
    
    private class ReporteCajon extends Thread{
        DefaultTableModel modelo1 = (DefaultTableModel) niv1.getModel();
        DefaultTableModel modelo2 = (DefaultTableModel) niv2.getModel();
        DefaultTableModel modelo3 = (DefaultTableModel) niv3.getModel();
        String consulta;
        
        @Override
        public void run(){
            //Nivel 1
            try {
                //Se borran las todas las filas cada que actualiza el hilo
                modelo1.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT Cajon, Placas from vehiculo WHERE Estado='activo' AND Nivel='1' ORDER BY Cajon ASC";
                ResultSet rs = stmt.executeQuery(consulta);
                    //Empieza el resultado en el primer registro
                    rs.first();
                    //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                    do {
                        //Se manda a un array los datos
                        String[] fila = {rs.getString(1), rs.getString(2)}; 
                        modelo1.addRow(fila);
                    } while (rs.next());//Hasta que ya no haya registros            
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Nivel 2
            try {
                //Se borran las todas las filas cada que actualiza el hilo
                modelo2.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT Cajon, Placas from vehiculo WHERE Estado='activo' AND Nivel='2' ORDER BY Cajon ASC";
                ResultSet rs = stmt.executeQuery(consulta);
                    //Empieza el resultado en el primer registro
                    rs.first();
                    //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                    do {
                        //Se manda a un array los datos
                        String[] fila = {rs.getString(1), rs.getString(2)}; 
                        modelo2.addRow(fila);
                    } while (rs.next());//Hasta que ya no haya registros            
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Nivel 3
            try {
                //Se borran las todas las filas cada que actualiza el hilo
                modelo3.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT Cajon, Placas from vehiculo WHERE Estado='activo' AND Nivel='3' ORDER BY Cajon ASC";
                ResultSet rs = stmt.executeQuery(consulta);
                    //Empieza el resultado en el primer registro
                    rs.first();
                    //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                    do {
                        //Se manda a un array los datos
                        String[] fila = {rs.getString(1), rs.getString(2)}; 
                        modelo3.addRow(fila);
                    } while (rs.next());//Hasta que ya no haya registros            
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        niv2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        niv3 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        niv1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reporte Cajones");
        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Nivel 1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Nivel 2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nivel 3");

        niv2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cajón", "Placas"
            }
        ));
        jScrollPane1.setViewportView(niv2);

        niv3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cajón", "Placas"
            }
        ));
        jScrollPane2.setViewportView(niv3);

        niv1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Cajón", "Placas"
            }
        ));
        jScrollPane3.setViewportView(niv1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Cajones ocupados por nivel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel1)
                .addGap(124, 124, 124)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(67, 67, 67))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(174, 174, 174))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ReporteCajones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteCajones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteCajones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteCajones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReporteCajones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable niv1;
    private javax.swing.JTable niv2;
    private javax.swing.JTable niv3;
    // End of variables declaration//GEN-END:variables
}
