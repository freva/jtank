package networking;

import game.Game;
import game.GameMultiplayer;
import gui.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Timer;

import objects.Tank;

public class Server implements Runnable {
	private static Tank[] players = new Tank[Main.maxNumPlayers+1];
	private static Connection[] connections = new Connection[Main.maxNumPlayers];
    private static ServerSocket serverSocket;
    private static int connectionSize = 0;
    
    private Server() {}
    
    public static void startServer() {
    	new Thread(new Server()).start();
    	players[0] = Game.getInstance().getPlayer();
    }
    
	public static void sendToEveryone(String msg) {
		for(int i=0; i < connectionSize; i++)
			connections[i].sendData(msg);
    }
	
	public static void sendToEveryoneExcept(String source, String msg) {
		for(int i=0; i < connectionSize; i++)
			if(!connections[i].getUsername().equals(source)) connections[i].sendData(msg);
    }
    
	public static void removeClientFromList(String username){
		for(int i=0; i < connectionSize; i++)
			if(connections[i].getUsername().equals(username)) connections[connectionSize--] = connections[i];
    }
	
	public static void addPlayer(Connection con, Tank player){
		connections[connectionSize++] = con;
		players[connectionSize] = player;
		Game.getInstance().addElement(player);
	}

	@Override
	public void run() {
		 try {        	
			 serverSocket = new ServerSocket(10001);
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
			for(int i=0; i<=connectionSize; i++) GameMultiplayer.addUpdate("1£" + players[i].toString());
			sendToEveryone(GameMultiplayer.getUpdates());
		}
	}
}