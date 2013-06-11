package networking;

import game.Game;
import game.GameMultiplayer;
import game.Level;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.Timer;
import tools.Base64Coder;

public class Client {
	private static PrintWriter outputWriter;
	private BufferedReader inputReader;
	private String receivedData;
	private Socket socket;
	
	public Client(String server, String username) throws ConnectException{
	     try{
	    	 socket = new Socket(server, 10001);
	    	 outputWriter = new PrintWriter(socket.getOutputStream(), true);
	    	 inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	 
		     sendMessage(username);
		     Level.setInstance(Base64Coder.decode(inputReader.readLine()), Base64Coder.decode(inputReader.readLine()));
	     }catch(ConnectException e){ throw new ConnectException();
	     }catch(IOException e){e.printStackTrace();}
	    
	     new Thread(new ClientListener()).start();
	     new Timer(100, new ClientUpdater()).start();
	}
	
	public static void sendMessage(String msg){
		outputWriter.println(msg);
	}
	
	class ClientUpdater implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			GameMultiplayer.addUpdate("1£" + Game.getInstance().getPlayer().toString());
			sendMessage(GameMultiplayer.getUpdates());
		}
	}
	
	class ClientListener implements Runnable {
		public void run(){
			try {
				Thread.sleep(500);
				while((receivedData = inputReader.readLine()) != null) NetworkParser.parseData(receivedData);
			} catch(IOException e){
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
