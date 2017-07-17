package client_server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Server {
	private ServerSocket server;
	private MainGUI mainGui;
	private GestioneContatti gc;
	private JList<Contatto> contatti_list;
	private Vector<Contatto> contatti;
	
	public Server(){
		try {
			server=new ServerSocket(2222);
			try{
				contatti=GestioneContatti.carica("contatti.con");
			}catch (Exception e) {
				contatti =new Vector<>();
			}
			contatti_list=new JList<>(contatti);
			gc=new GestioneContatti(contatti,contatti_list);
			mainGui=new MainGUI(contatti_list);
			gc.start();
			mainGui.getContattaBtn().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						Socket client= new Socket(mainGui.getIP().getText(),2222);
						PrintWriter pw=new PrintWriter(client.getOutputStream(),true);
						pw.println(mainGui.getMyName());
						BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
						String nome=br.readLine();
						gc.addContatto(new Contatto(nome,client.getInetAddress().getHostAddress()));
						ChatThread th=new ChatThread(client,nome);
						th.start();
						
					}catch(Exception e1){
						e1.printStackTrace();
					}
					
				}
			});
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		mainGui.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				try {
					gc.salva("contatti.con");
					server.close();
				} catch (IOException e1) {
					System.out.println("Impossibile chiudere il server!");
				}
				
			}
			
		});
		contatti_list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				String ip=contatti_list.getSelectedValue().ip;
				mainGui.setIP(ip);
				
			}
		});
	}
	public void vaiOnline(){
		
		while(true){
			try{
				Socket client=server.accept();
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				String nome=br.readLine();
				if(nome.equals("check")){
					continue;
				}
				PrintWriter pw=new PrintWriter(client.getOutputStream(),true);
				pw.println(mainGui.getMyName());
				Thread th=new ChatThread(client,nome);
				gc.addContatto(new Contatto(nome,client.getInetAddress().getHostAddress()));
				th.start();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void main(String[] args){
		Server tlc=new Server();
		tlc.vaiOnline();
	}

}