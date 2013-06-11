package networking;

import game.Game;
import game.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import objects.AbstractElementary;
import objects.Tank;
import tools.Base64Coder;

public class Connection implements Runnable {
    private Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputReader;
    private String username;

	/* Code User
	 * 0 - Message
	 * 1 - Change player status
	 * 2 - Object update
	 */
    public Connection(Socket socket) {
        this.socket = socket;
        
        try {
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        username = inputReader.readLine();
	        System.out.println(username + " has connected");
	        
	        outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getBackgroundByteArray()));
	        outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getLevelByteArray()));
	        
	        StringBuilder sb = new StringBuilder();
	        for(AbstractElementary ae : Game.getInstance().getElements()) sb.append("1£" + ae.toString()+ "%");
	        sb.deleteCharAt(sb.length()-1);
	        outputWriter.println(sb.toString());
	        
	        Server.addPlayer(this, new Tank(0, 0, username));
		} catch(IOException e) {
			e.printStackTrace();
		}
    }

    public void run() {
    	String receivedData;
    	try {
    		while((receivedData = inputReader.readLine()) != null) NetworkParser.parseData(receivedData);
    		
    		socket.close();
    	} catch (SocketException e) { e.printStackTrace();
    	} catch (IOException e) { e.printStackTrace();
        } finally {
    		System.out.println("Closing connection with " + username);
        	Server.removeClientFromList(username);
    	}
    }
    
    public void sendData(String msg) {
    	outputWriter.println(msg);
    }
    
    public String getUsername() {
    	return username;
    }
 }