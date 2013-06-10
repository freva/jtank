package networking;

import game.Game;
import game.Level;
import gui.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.Timer;
import objects.Tank;
import tools.Base64Coder;

public class Client {
	private static PrintWriter outputWriter;
	private BufferedReader inputReader;
	private String receivedData;
	private Socket socket;
	private static StringBuilder allUpdates = new StringBuilder();
	
	public Client(String server, String username) throws ConnectException{
	     try{
	    	 socket = new Socket(server, 10001);
	    	 outputWriter = new PrintWriter(socket.getOutputStream(), true);
	    	 inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	 
		     sendMessage(username);
		     Level.setInstance(Base64Coder.decode(inputReader.readLine()), Base64Coder.decode(inputReader.readLine()));	     
		     Main.mainFrame.add(new Game(false, username));
		     
		     String[] dataObjects = inputReader.readLine().split("£");
		     for(int i=0; i<dataObjects.length; i++) ObjectUpdater.interpretObject(dataObjects[i]);		     
	     }catch(ConnectException e){ throw new ConnectException();
	     }catch(IOException e){e.printStackTrace();}
	    
	     new Thread(new ClientListener()).start();
	     new Timer(100, new ClientUpdater()).start();
	}
	
	public static void sendMessage(String msg){
		outputWriter.println(msg);
	}
	
	public static void addUpdate(String update) {
		allUpdates.append(update + "%");
	}
	
	class ClientUpdater implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			allUpdates.append("1£" + Game.getPlayer().toString());
			sendMessage(allUpdates.toString());
			allUpdates = new StringBuilder();
		}
	}
	
	class ClientListener implements Runnable {
		public void run(){
			try {
				while((receivedData = inputReader.readLine()) != null){
					String[] dataObjects = receivedData.split("£");
					for(int i=0; i<dataObjects.length; i++) ObjectUpdater.interpretObject(dataObjects[i]);
				}
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
