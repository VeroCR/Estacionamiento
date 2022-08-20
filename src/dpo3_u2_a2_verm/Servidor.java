
package dpo3_u2_a2_verm;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class Servidor extends Observable implements Runnable{
        
        private int puerto;
        
        public Servidor (int puerto){
            this.puerto = puerto;
        }
        
        @Override
        public void run(){
            ServerSocket servidor = null;
            Socket sc = null;
            DataInputStream in;
            
            try{
                //socket del servidor
                servidor = new ServerSocket (puerto);
                System.out.println("Servidor iniciado.");
                System.out.println("Cliente conectado.");
                
                //Siempre estará escuchando las peticiones
                while(true){
                    sc = servidor.accept();
                    in = new DataInputStream(sc.getInputStream());
                    
                    //Leer el mensaje que se envía
                    String mensaje = in.readUTF();
                    
                    this.setChanged();
                    this.notifyObservers(mensaje);
                    this.clearChanged();
                    
                    //Cerrar socket
                    sc.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(AutorizarNomina.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Cliente desconectado.");
        }
    }
