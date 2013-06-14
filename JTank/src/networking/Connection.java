package networking;

import game.Game;
import game.GameMultiplayer;
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
    		while((receivedData = inputReader.readLine()) != null) {
    			String[] data = receivedData.split("%");
    			for(int i=0; i<data.length; i++){
    				String[] temp = data[i].split("£");
    				
    				if(temp[1].indexOf(Game.getInstance().getPlayer().getUsername()) == 0) continue;
    				switch(Integer.parseInt(temp[0])){
    				case 0: 
    					NetworkParser.parseMessage(temp[1]);
    					GameMultiplayer.addUpdate(data[i]);
    				break;
    				case 1:
    					NetworkParser.parseObject(temp[1]);
    				break;
    				case 2:
    					NetworkParser.parseDestroy(temp[1]);
    				break;
    				
    				}
    			}
    		}
    		
    		socket.close();
    	} catch (SocketException e) { e.printStackTrace();
    	} catch (IOException e) { e.printStackTrace();
        } finally {
        	GameMultiplayer.addUpdate("0£SERVER@" + username + " has left the game.");
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