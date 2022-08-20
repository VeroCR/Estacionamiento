/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dpo3_u2_a2_verm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vero_
 */
public class Cliente implements Runnable{
        
        private int puerto;
        private String mensaje;
        
        public Cliente (int puerto, String mensaje){
            this.puerto = puerto;
            this.mensaje = mensaje;
        }
        
        @Override
        public void run(){
            //Host del servidor
            final String HOST = "127.0.0.1";
            DataOutputStream out;
            
            try{
                Socket sc = new Socket(HOST, puerto);
                
                out = new DataOutputStream(sc.getOutputStream());
                
                //Envío mensaje al cliente
                out.writeUTF(mensaje);
                
                //Cerrar socket
                sc.close();
                
            } catch (IOException ex) {
                Logger.getLogger(AutorizarNomina.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
