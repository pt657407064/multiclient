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
       
        server = new ServerSocket(1000);
        Thread th = new Thread(new ServerThread(server));
        th.start();
               
    }

    
}
