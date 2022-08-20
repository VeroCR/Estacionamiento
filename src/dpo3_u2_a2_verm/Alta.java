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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author vero_
 */
public class Alta extends javax.swing.JFrame {
        //Llamar a la clase conexión para la base de datos
        Conexion con = new Conexion();
        Connection reg = con.conectarMySQL();
        Statement stmt;
        ResultSet rs;
        String fechaHora = "";
        
    /**
     * Creates new form Alta
     */
    public Alta() {
        initComponents();
        setLocationRelativeTo(null);          
    }
    
    private class altahilo extends Thread{
        
        @Override
        public void run(){
            try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            //Obtener hora actual
            fechaHora = dateFormat.format(date);
            //Enviar a un string los datos del formulario
            String sql = "INSERT INTO vehiculo VALUES ('" + ctMarca.getText() + "','" + ctModelo.getText() + "','" + ctColor.getText() + "','" + ctPlacas.getText() +
            "','" + ctCondiciones.getText() + "','" + ctCajon.getText() + "','" + ctNivel.getText() + "','" + ctAcomodador.getText() + "','" + fechaHora + "', null, null, 'Activo', '1')";
            stmt = reg.createStatement();
            //Mandarlo a la base de datos
            stmt.executeUpdate(sql);
            String folio = "INSERT INTO Cajones (Placas, idEmpleado) VALUES ('" + ctPlacas.getText() + "','" + ctAcomodador.getText() + "')";
            stmt.executeUpdate(folio);
            JOptionPane.showMessageDialog(null, "El vehiculo se registro exitosamente");
        } catch (SQLException ex) {
            Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    
    private class buscarhilo extends Thread{
        DefaultTableModel modelo = (DefaultTableModel) vehiculos.getModel();
        String consulta;
        @Override
        public void run(){
            if(cbTodos.isSelected()){
                try {
                //Se borran las todas las filas cada que actualiza el hilo
                modelo.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT * FROM vehiculo ORDER BY HoraEntrada";
                ResultSet rs = stmt.executeQuery(consulta);
                //Empieza el resultado en el primer registro
                rs.first();
                //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                do {
                    //Se manda a un array los datos de cada vehículo
                    String[] fila = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(12) };
                    //Se agrega una fila con los datos anteriores
                    modelo.addRow(fila);
                } while (rs.next());//Hasta que ya no haya registros
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }   
            }
            else{
               try {
                //Se borran las todas las filas cada que actualiza el hilo
                modelo.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT * FROM vehiculo WHERE Marca='" + ctMarca.getText() + "' AND Modelo LIKE'%" + ctModelo.getText() + "%' AND Color LIKE '%" + ctColor.getText() 
                + "%' AND Placas LIKE '%" + ctPlacas.getText() + "%' AND Condicionesvehiculo LIKE '" + ctCondiciones.getText() + "%' AND Cajon LIKE '%" + ctCajon.getText() +
                "%' AND Nivel LIKE '%" + ctNivel.getText() + "%' AND Acomodador LIKE '" + ctAcomodador.getText() + "%'";
                ResultSet rs = stmt.executeQuery(consulta);
                //Empieza el resultado en el primer registro
                rs.first();
                //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                do {
                    //Se manda a un array los datos de cada vehículo
                    String[] fila = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(12) };
                    //Se agrega una fila con los datos anteriores
                    modelo.addRow(fila);
                } while (rs.next());//Hasta que ya no haya registros
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "No hay registros con esa información, ingresa información en los cuadros de texto.");
            } 
            }
                
        }
    }
    
    private class consultarNivel extends Thread{
        String consulta;
        String nivel;
        String id;
        String autorizar = "1";
        int filasSel;
        @Override
        public void run(){
            filasSel = vehiculos.getSelectedRowCount();
            //Para ver si hay filas seleccionadas
            if(filasSel == 1){
            id = JOptionPane.showInputDialog("Digite su ID de empleado:");
                try {
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT NivelDeAcceso FROM trabajador where IdEmpleado = '" + id +"'";
                ResultSet rs = stmt.executeQuery(consulta);
                rs.first();
                nivel = rs.getString(1);
                    System.out.println(nivel);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Para ver si es administrador o no tiene privilegios    
            if (nivel.equals(autorizar)){
                Modificar mod = new Modificar();
                mod.start();
            }
            else{
                JOptionPane.showMessageDialog(null, "No tienes permisos para realizar cambios.");
            }
            }
            else{
                JOptionPane.showMessageDialog(null, "Selecciona la fila a modificar.");
            }
        }
    }
    
    private class Modificar extends Thread{
        @Override
        public void run(){
            int filaSelec = vehiculos.getSelectedRow();
            ctMarca.setText(vehiculos.getValueAt(filaSelec, 0).toString());
            ctModelo.setText(vehiculos.getValueAt(filaSelec, 1).toString());
            ctColor.setText(vehiculos.getValueAt(filaSelec, 2).toString());
            ctPlacas.setText(vehiculos.getValueAt(filaSelec, 3).toString());
            ctCondiciones.setText(vehiculos.getValueAt(filaSelec, 4).toString());
            ctCajon.setText(vehiculos.getValueAt(filaSelec, 5).toString());
            ctNivel.setText(vehiculos.getValueAt(filaSelec, 6).toString());
            ctAcomodador.setText(vehiculos.getValueAt(filaSelec, 7).toString());
        }
    }
    
    private class Actualizar extends Thread{
        @Override
        public void run(){
            int filaSelec = vehiculos.getSelectedRow();
        String horaEntrada = vehiculos.getValueAt(filaSelec, 8).toString();
        String sql = "UPDATE vehiculo SET Marca = '" + ctMarca.getText() + "', Modelo = '" + ctModelo.getText() + "', Color = '" + ctColor.getText() + "', Placas = '" + ctPlacas.getText() +
            "', CondicionesVehiculo = '" + ctCondiciones.getText() + "', Cajon = '" + ctCajon.getText() + "', Nivel = '" + ctNivel.getText() + "', Acomodador = '" + ctAcomodador.getText() 
            + "' WHERE HoraEntrada = '" + horaEntrada + "'";
            try {
                stmt = reg.createStatement();
                //Mandarlo a la base de datos
                stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(Alta.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Se ha actualizado correctamente.");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ctMarca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ctModelo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ctColor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ctPlacas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ctCondiciones = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ctCajon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ctNivel = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ctAcomodador = new javax.swing.JTextField();
        btGuardar = new javax.swing.JButton();
        btBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        vehiculos = new javax.swing.JTable();
        btModificar = new javax.swing.JButton();
        cbTodos = new javax.swing.JCheckBox();
        btActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Altas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setToolTipText("");

        jLabel1.setText("Ingrese los datos del vehículo:");

        jLabel2.setText("Marca:");

        jLabel3.setText("Modelo:");

        jLabel4.setText("Color:");

        jLabel5.setText("Placas:");

        jLabel6.setText("Condiciones del vehículo: ");

        jLabel7.setText("Cajón: ");

        jLabel8.setText("Nivel:");

        jLabel9.setText("Acomodador:");

        btGuardar.setText("Guardar");
        btGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGuardarActionPerformed(evt);
            }
        });

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        vehiculos.setBackground(new java.awt.Color(255, 204, 255));
        vehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Marca", "Modelo", "Color", "Placas", "Condiciones del vehículo", "Cajón", "Nivel", "Acomodador", "Hora de Entrada", "Hora de Salida", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(vehiculos);

        btModificar.setText("Modificar");
        btModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btModificarActionPerformed(evt);
            }
        });

        cbTodos.setText("Todos");
        cbTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTodosActionPerformed(evt);
            }
        });

        btActualizar.setText("Actualizar");
        btActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ctCondiciones)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ctColor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ctModelo)
                                    .addComponent(ctPlacas)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ctCajon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ctNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ctMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(19, 19, 19)
                                        .addComponent(ctAcomodador, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbTodos)
                            .addComponent(btGuardar))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btActualizar)
                            .addComponent(btBuscar))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(620, 620, 620)
                        .addComponent(btModificar)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ctMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ctModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ctColor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(ctPlacas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ctCondiciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(ctCajon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(ctNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(ctAcomodador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btGuardar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btActualizar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTodos)
                        .addComponent(btBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btModificar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    private void btGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGuardarActionPerformed
        altahilo alta = new altahilo();
        //Ejecutar el hilo
        alta.start();
    }//GEN-LAST:event_btGuardarActionPerformed

    private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
        buscarhilo buscar = new buscarhilo();
        buscar.start();
    }//GEN-LAST:event_btBuscarActionPerformed

    private void cbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTodosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTodosActionPerformed

    private void btModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btModificarActionPerformed
        consultarNivel niv = new consultarNivel();
        niv.start();
    }//GEN-LAST:event_btModificarActionPerformed

    private void btActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btActualizarActionPerformed
        Actualizar act = new Actualizar();
        act.start();
    }//GEN-LAST:event_btActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(Alta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Alta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btActualizar;
    private javax.swing.JButton btBuscar;
    private javax.swing.JButton btGuardar;
    private javax.swing.JButton btModificar;
    private javax.swing.JCheckBox cbTodos;
    private javax.swing.JTextField ctAcomodador;
    private javax.swing.JTextField ctCajon;
    private javax.swing.JTextField ctColor;
    private javax.swing.JTextField ctCondiciones;
    private javax.swing.JTextField ctMarca;
    private javax.swing.JTextField ctModelo;
    private javax.swing.JTextField ctNivel;
    private javax.swing.JTextField ctPlacas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable vehiculos;
    // End of variables declaration//GEN-END:variables
}
