package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class lec_03_prg_08_tcp_echo_server_multithread_chat {
	static ArrayList<Socket> list = new ArrayList<>();
	static int cnt = 0;
	public static void main(String[] args) throws IOException {
        
        ServerManager manager;
        Socket socket;
        ServerSocket server = new ServerSocket(65456);
        System.out.println("> echo-server is activated");
         
        while(true) {
            socket = server.accept(); 
            list.add(socket);
            cnt = cnt + 1;
            manager = new ServerManager(socket);
            manager.start();
        }
    }
}
class ServerManager extends Thread {
	    Socket socket; 
	    
	    public ServerManager(Socket socket) throws IOException {
	        this.socket = socket;
            InetAddress inetaddr = socket.getInetAddress();
            System.out.println("> cilent connected by " + inetaddr.getHostAddress() + 
            		" with Port number " + socket.getPort());
	    }
	 
	    public void run() {
	    	try{
	            InputStream in = socket.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            OutputStream out2 = socket.getOutputStream();
	            PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(out2));
	            
	            String line = null;
		            while((line = br.readLine()) != null){
		            	if (line.equals("quit")) {
		            		pw2.println(line);
		            		pw2.flush();
		            		break;
		            	}
		                sendMsg(line);
		            }
		        lec_03_prg_08_tcp_echo_server_multithread_chat.cnt -= 1;
            }catch(Exception e) {}
	    }
	     
	    public void sendMsg(String msg) throws IOException {
	        System.out.println("> received ("+ msg + ") and echoed to "
	        		+ lec_03_prg_08_tcp_echo_server_multithread_chat.cnt + "clients");
	        for(Socket soc : lec_03_prg_08_tcp_echo_server_multithread_chat.list) {
	        	OutputStream out = soc.getOutputStream();
	            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
	            pw.println(msg);
	            pw.flush();
	        }
	    }
}
