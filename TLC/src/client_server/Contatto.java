package client_server;

import java.io.Serializable;

public class Contatto implements Serializable{
	
	public String name,ip;
	public boolean online=false;
	public Contatto (String name,String ip){
		this.name=name;
		this.ip=ip;
		
	}
	public String toString(){
		if(online)	return name+"\t "+ip+"\t "+"Online";
		return name+"\t "+ip+"\t "+"Offline";
	}
	public boolean equals(Object o){
		if(!(o instanceof Contatto))return false;
		if(o==this)return true;
		Contatto c=(Contatto)o;
		return c.ip.equals(ip);
	}
}

