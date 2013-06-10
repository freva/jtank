package networking;

import game.Game;
import game.Level;
import gui.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import objects.DataWeapon;
import objects.Tank;
import objects.WeaponElementary;

import tools.Base64Coder;

public class Client {
	private PrintWriter outputWriter;
	private BufferedReader inputReader;
	private String receivedData, username;
	private Socket socket;
	
	public Client(String server, String username) throws ConnectException{
	     try{
	    	 this.username = username;
	    	 socket = new Socket(server, 10001);
	    	 outputWriter = new PrintWriter(socket.getOutputStream(), true);
	    	 inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	 
		     sendMessage(username);
		     Level.setInstance(Base64Coder.decode(inputReader.readLine()), Base64Coder.decode(inputReader.readLine()));
		     
		     String[] dataObjects = inputReader.readLine().split("£");
		     for(int i=0; i<dataObjects.length; i++) interpretObject(dataObjects[i]);

		     Main.mainFrame.add(new Game(false, username));
	     }catch(ConnectException e){ throw new ConnectException();
	     }catch(IOException e){e.printStackTrace();}
	    
	     Thread l = new Thread(new ClientListener());
	     l.start(); 
	}
	
	public void interpretObject(String s){
		String[] data = s.split("@");

		switch(Integer.parseInt(data[0])){
		case 0: 
			Tank t = new Tank(Float.parseFloat(data[1]), Float.parseFloat(data[2]), data[5]);
			t.setSpeed(Float.parseFloat(data[3]), Float.parseFloat(data[4]));
			t.setDeg(Float.parseFloat(data[6]));
			t.setCurrentWeapon(Integer.parseInt(data[7]));
			t.setAmmoForWeapon(Integer.parseInt(data[7]), Integer.parseInt(data[8]));
			Game.addElement(t);

			if(data[5].equals(username)) Game.setPlayer(t);
		break;
		
		case 1:
			Game.addElement(new WeaponElementary(Float.parseFloat(data[1]), Float.parseFloat(data[2]),
					Float.parseFloat(data[3]), Float.parseFloat(data[4]), DataWeapon.BULLET, Long.parseLong(data[5])));
		break;
		
		case 2:
			Game.addElement(new WeaponElementary(Float.parseFloat(data[1]), Float.parseFloat(data[2]),
					Float.parseFloat(data[3]), Float.parseFloat(data[4]), DataWeapon.GRENADE, Long.parseLong(data[5])));
		break;
		}
	}
	
	public void sendMessage(String msg){
		outputWriter.println(msg);	
	}	
	
	class ClientListener implements Runnable {
		public void run(){
			try {
				while((receivedData = inputReader.readLine()) != null){
					System.out.println(receivedData);
				}

			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
