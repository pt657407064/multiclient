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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static Socket socket;
    public static DataOutputStream dos;
    public static DataInputStream dis;
    public static Scanner sc = new Scanner(System.in);
    static String text;
    static String message;
    public static void main(String[] args) throws IOException{
      try{
            connectToServer();
            setupStream();
            chatting();     
        
      }
      catch(Exception e)
      {
          
      }
      finally{
          socket.close();
      }
    }

    private static void connectToServer() throws IOException {
        System.out.println("Connecting to sever.......");
        socket = new Socket("127.0.0.1",1000);
        System.out.println("Connected to server!!");
    }

    private static void setupStream() throws IOException {
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        dos.flush();
    }

    private static void chatting() throws IOException {

        Thread incomingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                
                try {
                    while(!socket.isClosed())
                    {
                        message = dis.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException ex) {
                }
            }
        });
        incomingThread.start();        
        do{
            text = sc.nextLine();
            dos.writeUTF(text);
        }while(!socket.isClosed());
    }
    
}
