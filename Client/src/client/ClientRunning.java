/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tao
 */
public class ClientRunning {
    public  Socket socket;
    public  static DataOutputStream dos;
    public  static DataInputStream dis;
    private String message;
    public ClientRunning() throws IOException
    {
        try {
            connectToServer();
            setupStream();
            chatting();             
        } catch (IOException ex) {
            Logger.getLogger(ClientRunning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToServer() throws IOException {
        clientUI.appendText("Coneccting to server");
        socket = new Socket("127.0.0.1",10000);
        clientUI.appendText("Coneccted to server");
    }

    private void setupStream() throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        dos.flush();
    }

    private void chatting() throws IOException {
        
        new Thread(new Runnable(){
            @Override
            public void run() {
            do{
                try {
                    message = dis.readUTF();
                    clientUI.appendText(message);
                } catch (IOException ex) {
                    Logger.getLogger(ClientRunning.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(!socket.isClosed());
            }
            
        }).start();
       
                  
    }

    private void close() throws IOException {
        dis.close();
        dos.close();
        socket.close();
    }
}
