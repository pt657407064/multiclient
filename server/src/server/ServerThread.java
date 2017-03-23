/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tao
 */
public class ServerThread implements Runnable {
    
    ServerSocket server;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    Socket client;
    String incoming;
    Scanner sc = new Scanner(System.in);
    public static ArrayList<clientHandler> clients = new ArrayList<>();
    String text;
    public ServerThread(ServerSocket server)
    {
        this.server = server;
    }
    @Override
    public void run() {
        try {
           
            while(!server.isClosed())
            {
                waiting();
            }
      
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
    }

   

   

    private void waiting() throws IOException {
       // System.out.println("Waiting for client to connect....");
        client = server.accept();  
        clientHandler clientThreadObj = new clientHandler(client);
        Thread thread = new Thread(clientThreadObj);
        thread.start();
        clients.add(clientThreadObj);
        System.out.println("Someone is connected");
        
        if(clients.size() == 1)
        {
        Thread outgoingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                while(!server.isClosed())
                {
                    
                        text = sc.nextLine();
                        for(int i = 0;i<clients.size();i++)
                        {
                            if(clients.get(i).client.isClosed())
                            {   clients.remove(i);}
                        }
                        for(int i = 0;i<clients.size();i++)
                        {
                            if(!clients.get(i).client.isClosed())
                            {clients.get(i).getDos().writeUTF(text);}
                            
                        }
                   
                 }
                } catch (Exception ex) {
                    System.out.println("crashed 2");
                  }
                
                
            }

      
           });
                outgoingThread.start();
         }
    
    }
}
