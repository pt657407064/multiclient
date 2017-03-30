/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author tao
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static ServerSocket server;
    
    public static void main(String[] args) throws IOException {
       
        new ServerUI();  
        
           
      
    }
    public static void setServer(ServerSocket server)
    {
        Server.server = server;
    }

    
}
