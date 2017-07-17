package client_server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatThread extends Thread {
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private ChatGUI chatgui;
	private String name;
	
	public ChatThread (Socket socket,String nome) throws IOException{
		
		this.socket=socket;
		this.name=nome;
		br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		pw=new PrintWriter(socket.getOutputStream(),true);
		
		chatgui=new ChatGUI();
		chatgui.setTitle("Chat w/ "+name);
		
		
		chatgui.getInviaBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pw.println(chatgui.getMessageTxt().getText());
					chatgui.println("Me: "+chatgui.getMessageTxt().getText());
					chatgui.getMessageTxt().setText("");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
	}
	
	@Override
	public void run(){
		
		while(true){
			try {
				String line=br.readLine();
				chatgui.println(name+": "+line);
			} catch (IOException e) {
				disconnetti();
				break;
			} catch (InterruptedException e) {
				disconnetti();
				break;
			}
		}
		
	}
	private void disconnetti(){
			try {
				socket.close();
				br.close();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

}