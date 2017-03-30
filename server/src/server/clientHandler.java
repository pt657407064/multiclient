/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static server.ServerThread.dis;
import static server.ServerThread.dos;

/**
 *
 * @author Tao
 */
public class clientHandler implements Runnable{

    public Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }
    String incoming;
    Scanner sc = new Scanner(System.in);
    public clientHandler(Socket client)
    {
        this.client = client;
    }
    @Override
    public void run() {
        try {
            setUpStream();
            chatting();
        } catch (IOException ex) {
            Logger.getLogger(clientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        
    }

    private void setUpStream() throws IOException {
        dis = new DataInputStream(client.getInputStream());
        dos = new DataOutputStream(client.getOutputStream());
        dos.flush();
     
    }

    private void chatting() {
        Thread incomingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(!client.isClosed())
                    {
                        incoming = dis.readUTF();
                        ServerUI.appendText(incoming);
                        for(int i = 0; i < ServerThread.clients.size();i++)
                        {
                            if(ServerThread.clients.get(i).client!=client)
                            {
                                ServerThread.clients.get(i).getDos().writeUTF(incoming);
                                ServerThread.clients.get(i).getDos().flush();
                            }
                        }
                    }
                } catch (Exception ex) {
                      
                    try {
                        ServerUI.appendText("Client disconnected");
                        dis.close();
                        dos.close();
                        client.close();
                        if(ServerThread.clients.isEmpty()){ServerUI.appendText("No one is connected");}
                    } catch (IOException ex1) {
                        ServerUI.appendText("Client disconnected");
                    }
             
                } 

            }
        });
        
       
        incomingThread.start();

        
    }
    
}
