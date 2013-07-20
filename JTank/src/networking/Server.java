package networking;

import game.Game;
import gui.Main;
import gui.chat.Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Timer;

import objects.Tank;

public class Server implements Runnable {
	private static Connection[] connections = new Connection[Main.maxNumPlayers];
	private static int connectionSize = 0;
    
    private Server() {}
    
    public static void startServer() {
    	new Thread(new Server()).start();
    }
    
	public static void sendToEveryone(String msg) {
		for(int i=0; i < connectionSize; i++)
			connections[i].sendData(msg);
    }

	public static void removeClientFromList(String username){
		for(int i=0; i < connectionSize; i++)
			if(connections[i].getUsername().equals(username)) connections[connectionSize--] = connections[i];
		Game.getInstance().removeElement(Game.getInstance().getElement(username));
    }
	
	public static void addPlayer(Connection con, Tank player){
		connections[connectionSize++] = con;
		Game.getInstance().addElement(player);
	}
	
	public static void announce(String msg){
        Game.getInstance().addUpdate("0£SERVER@" + msg);
        Chat.getInstance().addMessage("SERVER", msg);
	}

	@Override
	public void run() {
		 try {
			 ServerSocket serverSocket = new ServerSocket(10001);
			 new Timer(100, new ServerUpdater()).start();

			 while(true) {
				 Socket socket = serverSocket.accept();
				 if(connectionSize == Main.maxNumPlayers-1) continue;

				 new Thread(new Connection(socket)).start();
			 }
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	class ServerUpdater implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Game.getInstance().addUpdate("1£" + Game.getInstance().getPlayer().toString());
			sendToEveryone(Game.getInstance().getUpdates());
		}
	}
}