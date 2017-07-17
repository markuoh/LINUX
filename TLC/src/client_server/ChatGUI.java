package client_server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Font;

public class ChatGUI extends JFrame {

	private JPanel sfondo;
	private JTextArea textChat;
	private Semaphore mutex;
	private JTextArea message_txt;
	private JButton invia_btn; 
	
	public ChatGUI() {
		setTitle("\"Chat w/ host\"");
		mutex=new Semaphore(1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 674);
		sfondo = new SfondoChat();
		sfondo.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(sfondo);
		sfondo.setLayout(null);
		
		message_txt = new JTextArea();
		message_txt.setFont(new Font("Monospaced", Font.PLAIN, 15));
		message_txt.setBounds(12, 523, 512, 91);
		sfondo.add(message_txt);
		textChat = new JTextArea();
		textChat.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textChat.setEditable(false);
		textChat.setBounds(12, 13, 512, 497);
		sfondo.add(textChat);
		invia_btn = new JButton("Invia");
		invia_btn.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 21));
		invia_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		invia_btn.setBackground(Color.BLACK);
		invia_btn.setBounds(536, 553, 108, 31);
		sfondo.add(invia_btn);
		setVisible(true);
	}
	public JButton getInviaBtn(){
		return invia_btn;
	}
	public JTextArea getMessageTxt(){
		return message_txt;
	}
	public void println(String line) throws InterruptedException{
		mutex.acquire();
		textChat.setText(textChat.getText()+line+"\n");
		mutex.release();
	}
}