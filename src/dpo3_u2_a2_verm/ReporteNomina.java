/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dpo3_u2_a2_verm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author vero_
 */
public class ReporteNomina extends javax.swing.JFrame {
    //Llamar a la clase conexión para la base de datos
        Conexion con = new Conexion();
        Connection reg = con.conectarMySQL();
        Statement stmt;
        ResultSet rs;
    /**
     * Creates new form ReporteNomina
     */
    public ReporteNomina() {
        initComponents();
        setLocationRelativeTo(null); 
    }
    
    private class reporte extends Thread{
    
    @Override
    public void run(){
        //Variables para generar la ruta del txt
        String nom = "ReporteNomina";
        String ruta = System.getProperty("user.dir");
        String rutaCom = ruta + "/" + nom + ".txt";
        FileWriter ubicacion = null;
        try{
            ubicacion = new FileWriter(rutaCom);
            BufferedWriter escritor = new BufferedWriter(ubicacion);
            String consulta;
            int c = 0;
            //String para obtener la fecha de inicio
            String inicio = ctInicio.getText()+" 00:00:00";
            //String para obtener la fecha final
            String fin = ctFinal.getText()+" 23:59:59";
            escritor.write("________________________________________________________________________________________\n");
            escritor.write("                        **Reporte de pago de nómina**\n");
            escritor.write("________________________________________________________________________________________\n");
            escritor.write("| Posición | Nombre Trabajador | No. Servicios |    Bono    |   Salario    |Total general|\n");
            escritor.write("________________________________________________________________________________________\n");
            stmt = reg.createStatement();
                //Consulta MySQL 
                consulta = "SELECT Nombre, SUM(Valor) FROM vehiculo, trabajador WHERE vehiculo.Acomodador=Trabajador.idEmpleado AND HoraEntrada BETWEEN '" + 
                        inicio + "' AND '" + fin + "' GROUP BY Acomodador ORDER BY SUM(Valor) DESC, Acomodador";
                rs = stmt.executeQuery(consulta);
                //Empieza el resultado en el primer registro
                rs.first();
                //do while para que muestre los datos actualizados en la tabla hasta que se terminen los registros
                do {
                    //contador para ver el lugar en el que se encuentra el acomodador
                    c++;
                    //String para obtener el nombre
                    String nombre = rs.getString(1);
                    //String para obtener el número de servicios
                    String sumVal = rs.getString(2);
                    //Se convierte a int el número de servicios
                    int serv = Integer.parseInt(sumVal);
                    //Se obtiene el promedio por día dividiéndolo entre 7
                    double prom = serv/7;
                    //Salario semanal
                    int pago = 2500;
                    String sal = "2,500.00";      
                    String total;
                    String bono;
                    //if else para verificar que los primeros 5 reciban bono 
                    if(c<=5){
                        pago = pago + 250;
                        total = "$"+ pago +".00";
                        bono = "$250.00";
                    }
                    else{
                        bono = "$0.00   ";
                        total = "$"+ pago +".00";     
                    }
                    //Se manda a un array los datos de cada Acomodador
                    escritor.write("|    "+ c +".   | " + nombre + " |     " + sumVal + "   |   " + bono + "    |  " + sal + "  |  " + total + "    |");
                    escritor.newLine();
                    } while (rs.next());//Hasta que ya no haya registros
                escritor.write("________________________________________________________________________________________\n");
                escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(ReporteNomina.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReporteNomina.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            //Se selecciona el archivo de texto generado anteriormente
            FileReader fr = new FileReader(rutaCom);
            BufferedReader br = new BufferedReader(fr);
            String texto = "";
            String linea = "";
            while (((linea = br.readLine())!=null)){
                texto+=linea+"\n";
            }
            //Se muestra en el textArea el reporte
            taRep.setText(texto);
        }
        catch(Exception e){ 
        }
        JOptionPane.showMessageDialog(null, "Reporte generado correctamente en "+ rutaCom);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        taRep = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        ctInicio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ctFinal = new javax.swing.JTextField();
        btGenerar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reporte de nómina");
        setBackground(new java.awt.Color(204, 204, 255));

        taRep.setColumns(20);
        taRep.setRows(5);
        jScrollPane1.setViewportView(taRep);

        jLabel1.setText("Escriba las fechas para generar el reporte (aaaa-mm-dd):");

        jLabel2.setText("Inicio:");

        jLabel3.setText("Final:");

        btGenerar.setText("Generar");
        btGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btGenerar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(167, 167, 167)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(ctInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(ctFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(135, 135, 135))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ctInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ctFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(btGenerar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGenerarActionPerformed
        reporte rep = new reporte();
        rep.start();
    }//GEN-LAST:event_btGenerarActionPerformed

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
            java.util.logging.Logger.getLogger(ReporteNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteNomina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReporteNomina().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGenerar;
    private javax.swing.JTextField ctFinal;
    private javax.swing.JTextField ctInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taRep;
    // End of variables declaration//GEN-END:variables
}
