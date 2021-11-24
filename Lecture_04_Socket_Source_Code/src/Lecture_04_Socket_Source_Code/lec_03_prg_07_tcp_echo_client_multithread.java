package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class lec_03_prg_07_tcp_echo_client_multithread {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 65456);
        System.out.println("> echo-cilent is activated");
        ClientReceiver receiver = new ClientReceiver(socket);
        ClientSender sender = new ClientSender(socket);      
        receiver.start();
        sender.start();
    }
}

class ClientReceiver extends Thread {
    Socket socket;
    BufferedReader recv;
     
    public ClientReceiver(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
    	try {
    		recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        while(true) {
	        	String s = recv.readLine();
    			if (s.equals("quit")) {
    				break;
    			}
	                System.out.println("> received: " + s);  
	        }
            socket.close();
            System.out.println("> echo-cilent is de-activated");
    	}catch (IOException e) {
                e.printStackTrace();
        }
    }  
}

class ClientSender extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter pwSend;
    
    public ClientSender(Socket socket) throws IOException {
        this.socket = socket;
    }
 
    public void run() {
    	try {
    		pwSend = new PrintWriter(socket.getOutputStream());
    		in = new BufferedReader(new InputStreamReader(System.in));

    		while(true) {
    			System.out.print("> ");
    			String s = in.readLine();
    			pwSend.println(s);
    			pwSend.flush();
    			if (s.equals("quit")) {
    				break;
    			}
    		}
    	}catch(IOException e) {}
    }
}
