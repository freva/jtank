package networking;

import game.Game;
import gui.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static Connection[] connections = new Connection[Main.maxNumPlayers];
    private static Connection service;
    private static ServerSocket serverSocket;
    private static int connectionSize = 0;
    
    public static void sttartServer(Game game) {
        try {        	
            serverSocket = new ServerSocket(10001);

            while(true) {
                Socket socket = serverSocket.accept();
                if(connectionSize == Main.maxNumPlayers-1) continue;
                
                service = new Connection(socket);
                Thread t = new Thread(service);
                t.start();
                
                connections[connectionSize++] = service;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}