/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author tao
 */
public class clientUI extends JFrame implements ActionListener,KeyListener {
    
    public static JTextArea chatHistory;
    public JTextArea typingArea;
    public static JScrollPane chatHistoryScroll;
    public JScrollPane typingAreaScroll;
    public JButton sendButton;
    public JButton connectButton;
    
    public clientUI()
    {
        super("Client");
        creatUI();
    }

    private void creatUI() {

      chatHistory = new JTextArea();
      chatHistory.setWrapStyleWord(true);
      chatHistory.setLineWrap(true);
      
      
      typingArea = new JTextArea();
      typingArea.setWrapStyleWord(true);
      typingArea.setLineWrap(true);
      typingArea.setEnabled(true);
      typingArea.addKeyListener(this);
      
      chatHistoryScroll = new JScrollPane(chatHistory);
      chatHistoryScroll.setBounds(0, 0, 280, 380);
      chatHistory.setEditable(false);
      
      typingAreaScroll = new JScrollPane(typingArea);
      typingAreaScroll.setBounds(0,400,180,100);
      
      
      
      sendButton = new JButton("Send");
      sendButton.addActionListener(this);
      sendButton.setBounds(200,380,100,100);

      connectButton = new JButton("Connect");
      connectButton.addActionListener(this);
      connectButton.setBounds(300,450,75,50);
      
      this.setLayout(null);
      this.setSize(new Dimension(400,550));
      this.add(typingAreaScroll);
      this.add(chatHistoryScroll);
      this.add(connectButton);
      this.add(sendButton);
      this.setVisible(true);
      this.setResizable(false);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





    }
    
    public static void appendText(String text)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatHistory.append(text+"\n");
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==connectButton)
        {
            try {
                new ClientRunning();
                connectButton.setEnabled(false);
            } catch (IOException ex) {
                Logger.getLogger(clientUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            String msg = typingArea.getText();
            try {
                ClientRunning.dos.writeUTF(msg);
                appendText(msg);
                typingArea.setText("");
                
            } catch (IOException ex) {
                appendText("Something is wrong sending message");
            }
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                e.consume();
                sendButton.doClick();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
