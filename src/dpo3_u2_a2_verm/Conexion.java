/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dpo3_u2_a2_verm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vero_
 */
public class Conexion {
    // Variables para la conexión
    private static Connection conn;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/estacionamiento";
    private static final String user = "root";
    private static final String password = "";

    public Connection conectarMySQL() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null){
                System.out.println("Conexion establecida...");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar "+ e);
        }
        return conn;
    }

}
