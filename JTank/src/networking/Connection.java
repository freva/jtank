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
	 * 1 - AbstractElementary update&creation
	 * 2 - AbstractElementary uptate+deletion
	 */
    public Connection(Socket socket) {
        this.socket = socket;
        
        try {
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
	        username = inputReader.readLine();
	       	        
	        outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getBackgroundByteArray()));
	        outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getLevelByteArray()));
	        
	        StringBuilder sb = new StringBuilder();
	        for(AbstractElementary ae : Game.getInstance().getElements()) sb.append("1£").append(ae.toString()).append("%");
	        sb.deleteCharAt(sb.length()-1);
	        outputWriter.println(sb.toString());
	        
	        Server.addPlayer(this, new Tank(0, 0, username));
	        Server.announce(username + " has joined the game.");
		} catch(IOException e) {
			e.printStackTrace();
		}
    }

    public void run() {
    	String receivedData;
    	try {
    		while((receivedData = inputReader.readLine()) != null) {
    			NetworkParser.parseData(receivedData);
    			Game.getInstance().addUpdate(receivedData.substring(0, receivedData.length()-1));
    		}
    		
    		socket.close();
    	} catch (SocketException e) {
    	} catch (IOException e) { e.printStackTrace();
        } finally {
        	Server.announce(username + " has left the game.");
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