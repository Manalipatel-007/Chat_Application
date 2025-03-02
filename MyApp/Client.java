package MyApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	Socket socket ;
	BufferedReader br;
	PrintWriter out;
	
	
	public Client()
	{
		try {
			System.out.println("Sending request to server");
			socket = new Socket("127.0.0.1", 6666);
			System.out.println("Connection Done.");
			
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	
	    	out = new PrintWriter(socket.getOutputStream());
	    	
	    	startReading();
	    	startWriting();
		} catch(Exception e){
			
			
		}
	}
	
	public void startReading()
	{
		//thread - read data
		Runnable r1=()->{
			
			System.out.println("reader started..");
			
			try {
			while(true) {
				     
				    String msg = br.readLine();
			        if(msg.equals("exit"))
			        {
			        	System.out.println("Server terminated the chat");
			        	socket.close();
			        	break;
			        }
			        	
			        System.out.println("Server : "+msg);
				 } 
			}
			catch(Exception e) {
				// e.printStackTrace();
				 System.out.println("Connection Closed !");
			 }
		};
		
		new Thread(r1).start();
	}
	
	public void startWriting()
	{
		//thread - read data from the user and send to the client
		Runnable r2=()->{
			System.out.println("writer started..");
			try {
			while(!socket.isClosed()) {
					
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					
					out.println(content);
					out.flush();
					if(content.equals("exit")) {
						socket.close();
						break;
					}
					
					
				} 
			System.out.println("Connection Closed !");
			}
			catch(Exception e){
				e.printStackTrace();
				//System.out.println("Connection Closed !")
			}
		};

		new Thread(r2).start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is client !!");
        new Client();
	}

}
