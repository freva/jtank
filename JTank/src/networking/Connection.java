package networking;

import game.Game;
import game.Level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import objects.Tank;
import tools.Base64Coder;

public class Connection implements Runnable {
    private Socket socket;
    private PrintWriter outputWriter;
    private BufferedReader inputReader;
	private Tank user;

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
            
            user = new Tank(100, 100, inputReader.readLine());
            Game.addElement(user);
            System.out.println(user.getUsername() + " has connected");
            
            outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getBackgroundByteArray()));
            outputWriter.println(Base64Coder.encodeLines(Level.getInstance().getLevelByteArray()));
            
    		while((receivedData = inputReader.readLine()) != null){
    			System.out.println(receivedData);
    		}
    		
    		socket.close();
    	} catch (SocketException e) { e.printStackTrace();
    	} catch (IOException e) { e.printStackTrace();
        } finally {
    		System.out.println("Closing connection with " + user.getUsername());
        	Server.removeClientFromList(user.getUsername());
    	}
    }
    
    public void sendData(String msg) {
    	outputWriter.println(msg);
    }
    
    public String getUsername() {
    	return user.getUsername();
    }
 }