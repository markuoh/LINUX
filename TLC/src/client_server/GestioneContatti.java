package client_server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JList;

public class GestioneContatti extends Thread{
	private Vector<Contatto> vettore ;
	private JList <Contatto> lista;
	private Semaphore mutex=new Semaphore(1);
	
	public GestioneContatti(Vector<Contatto> vettore,JList <Contatto>lista ){
		this.vettore=vettore;
		this.lista=lista;
	}
	public void run(){
		while(true){
			try {
				mutex.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			for(int i=0;i<vettore.size();i++){
				Socket prova=new Socket();
				InetSocketAddress isa=new InetSocketAddress(vettore.get(i).ip, 2222);
				try{
					prova.connect(isa, 1000);     
					PrintWriter pw=new PrintWriter(prova.getOutputStream(),true);
					pw.println("check");
					pw.close();
					vettore.get(i).online=true;
					lista.repaint();
					prova.close();
				}catch (SocketTimeoutException e) {
					vettore.get(i).online=false;
					lista.repaint();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			try{
				mutex.release();
				Thread.sleep(1000);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void salva(String file) throws FileNotFoundException, IOException{
		for(int i=0;i< vettore.size();i++){
			vettore.get(i).online=false;
		}
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(vettore);
		oos.close();	
	}
	@SuppressWarnings("unchecked" )
	public static Vector<Contatto> carica(String file) throws ClassNotFoundException, IOException{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
		Object o=ois.readObject();
		if(o instanceof Vector){
			return (Vector<Contatto>)o;
		}
		return null;
	}
	public void addContatto(Contatto contatto){
		if(contatto.ip.equals("127.0.0.1"))return;
		try {
			mutex.acquire();
			for (Contatto c:vettore){
				if(c.equals(contatto)){
					mutex.release();
					return;
				}
			}
			vettore.add(contatto);
			lista.setListData(vettore);
			lista.repaint();
			mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}