package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
	private PrintWriter outputWriter;
	private BufferedReader inputReader;
	private String server = "localhost", receivedData;
	
	public Client(String username) throws ConnectException{
	     try{
	    	 Socket socket = new Socket(server, 10001);
	    	 outputWriter = new PrintWriter(socket.getOutputStream(), true);
	    	 inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	 
		     sendMessage(username);		     
	     }catch(ConnectException e){ throw new ConnectException("Kunne ikke koble til " + server);
	     }catch(IOException e){e.printStackTrace();}
	    
	     Thread l = new Thread(new ClientListener());
	     l.start(); 
	}
	
	public void sendMessage(String msg){
		outputWriter.println(msg);	
	}	
	
	class ClientListener implements Runnable {
		public void run(){
			try {
				while((receivedData = inputReader.readLine()) != null){
					System.out.println(receivedData);
				}

			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}