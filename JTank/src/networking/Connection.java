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
	private StringBuilder sb = new StringBuilder();

	/* Code User
	 * 0 - Message
	 * 1 - Change player status
	 * 2 - Object update
	 */
    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
    	String receivedData;
    	try {
    		outputWriter = new PrintWriter(socket.getOutputStream(), true);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            username = inputReader.readLine();
            System.out.println(username + " has connected");
            
            outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getBackgroundByteArray()));
            outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getLevelByteArray()));
            
            for(AbstractElementary ae : Game.getElements()){
            	sb.append(ae.toString());
            	sb.append("£");
            }
            
            outputWriter.println(sb.toString());
            
    		while((receivedData = inputReader.readLine()) != null){
    			String[] data = receivedData.split("%");
    			
    			for(int i=0; i<data.length; i++){
    				String[] temp = data[i].split("£");
    				
    				switch(Integer.parseInt(temp[0])){
        			case 1:
        				ObjectUpdater.interpretObject(temp[1]);
        			break;
        			
        			}
    			}
    		}
    		
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