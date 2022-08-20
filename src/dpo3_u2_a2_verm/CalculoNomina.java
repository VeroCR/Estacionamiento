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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vero_
 */
public class CalculoNomina extends javax.swing.JFrame {
    //Llamar a la clase conexión para la base de datos
        Conexion con = new Conexion();
        Connection reg = con.conectarMySQL();
        Statement stmt;
        ResultSet rs;
    /**
     * Creates new form CalculoNomina
     */
    public CalculoNomina() {
        initComponents();
        setLocationRelativeTo(null); 
    }
    
    private class calculo extends Thread{
        DefaultTableModel modelo = (DefaultTableModel) Trabajadores.getModel();
        String consulta;
        @Override
        public void run(){
            if(cbTodos.isSelected()){
                try {
                //Se borran las todas las filas cada que actualiza el hilo
                int c = 0;
                modelo.setRowCount(0);
                stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT Acomodador, Nombre FROM vehiculo, trabajador WHERE vehiculo.Acomodador=Trabajador.idEmpleado GROUP BY Acomodador ORDER BY SUM(Valor) DESC, Acomodador";
                rs = stmt.executeQuery(consulta);
                //Empieza el resultado en el primer registro
                rs.first();
                //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                do {
                    //contador para ver en qué lugar se encuentra el empleado
                    c++;
                    //Se obtiene el sueldo del cuadro de texto
                    int pago = Integer.parseInt(ctSueldo.getText());
                    String total;
                    //if else para asignar el bono a los 5 primeros
                    if(c<=5){
                        //Está dentro de los primeros 5, se obtiene el bono de un 10% 
                        pago = pago + ((pago*10)/100) ;
                        total = "$"+ pago +".00";
                    }
                    else{
                        //Pago normal
                        total = "$"+ pago +".00";     
                    }
                    //Se manda a un array los datos de cada Acomodador
                    String[] fila = {rs.getString(1), rs.getString(2), total }; 
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
                //Se obtiene el id del acomodador
                String idAcomodador = ctidEmpleado.getText();
                //Se convierte a int para realizar una comparación más adelante
                int idA = Integer.parseInt(idAcomodador);
                int a = 0;
                //Obtener el sueldo
                int pago = Integer.parseInt(ctSueldo.getText());
                String total;
                //Consulta MySQL para obtener el nombre del acomodador
                String cons = "SELECT Nombre FROM Trabajador WHERE idEmpleado = '" + idAcomodador + "'";
                rs = stmt.executeQuery(cons);
                rs.first();
                String nombre = rs.getString(1);
                //Consulta para comparar con todos los acomodadores y obtener en qué lugar se encuentra para ver si le dan bono
                consulta = "SELECT Acomodador FROM vehiculo GROUP BY Acomodador ORDER BY SUM(Valor) DESC, Acomodador";
                ResultSet rs = stmt.executeQuery(consulta);
                int sal = 0;
                //Empieza el resultado en el primer registro
                rs.first();
                do{
                    //Se obtiene el id del acomodador
                    String idEmp = rs.getString(1);
                    //Se convierte a int para compararlo
                    int idB = Integer.parseInt(idEmp);
                    //Se compara con un if else
                    if(idA != idB){
                        //Si no son iguales se aumenta el contador
                        a++;
                    }
                    else{
                        //Si es igual, sale del ciclo
                         sal = 1;
                    }
                    rs.next();
                }while(sal == 0);//Hasta que sean iguales los dos id
                    //if else para verificar si le toca bono de acuerdo al número de iteraciones que realizó el ciclo
                    if(a<5){
                        //Está dentro de los primeros 5, se obtiene el bono de un 10% 
                        pago = pago + ((pago*10)/100) ;
                        total = "$"+ pago +".00";
                        
                    }
                    else{
                        //Pago normal
                        total = "$"+ pago +".00";
                    }
                    String[] fila = {idAcomodador, nombre, total }; 
                    //Se agrega una fila con los datos anteriores
                    modelo.addRow(fila);
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Ingresa un ID válido.");
            } 
            }
                
        }
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
        ctidEmpleado = new javax.swing.JTextField();
        cbTodos = new javax.swing.JCheckBox();
        btCalcular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Trabajadores = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        ctSueldo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cálculo de nómina");
        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setText("Id Empleado:");

        cbTodos.setText("Todos");

        btCalcular.setText("Calcular");
        btCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalcularActionPerformed(evt);
            }
        });

        Trabajadores.setBackground(new java.awt.Color(255, 204, 255));
        Trabajadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IdTrabajador", "Nombre", "Sueldo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Trabajadores);

        jLabel2.setText("Sueldo: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ctidEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(ctSueldo))
                        .addGap(36, 36, 36)
                        .addComponent(cbTodos)
                        .addGap(18, 18, 18)
                        .addComponent(btCalcular)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ctidEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ctSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbTodos)
                            .addComponent(btCalcular))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCalcularActionPerformed
        calculo cal = new calculo();
        cal.start();
    }//GEN-LAST:event_btCalcularActionPerformed

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
            java.util.logging.Logger.getLogger(CalculoNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculoNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculoNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculoNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculoNomina().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Trabajadores;
    private javax.swing.JButton btCalcular;
    private javax.swing.JCheckBox cbTodos;
    private javax.swing.JTextField ctSueldo;
    private javax.swing.JTextField ctidEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
