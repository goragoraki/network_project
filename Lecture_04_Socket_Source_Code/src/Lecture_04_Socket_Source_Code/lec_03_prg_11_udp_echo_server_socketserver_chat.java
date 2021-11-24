package Lecture_04_Socket_Source_Code;

import java.net.*; 
import java.util.HashSet;
 
public class lec_03_prg_11_udp_echo_server_socketserver_chat {	
 
	private static HashSet<Integer> portSet = new HashSet<Integer>();
	public static void main(String args[]) throws Exception {
      
        int serverport = 65456;        
	    DatagramSocket udpServerSocket = new DatagramSocket(serverport);        
 
	    System.out.println("echo-server is activated");
 
	    while(true)
		{
			byte[] receiveData = new byte[1024];          
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			udpServerSocket.receive(receivePacket);           
			String clientMessage = (new String(receivePacket.getData())).trim();
			InetAddress clientIP = receivePacket.getAddress();           
			int clientport = receivePacket.getPort();
			if(clientMessage.equals("#REG")) {
				portSet.add(clientport);
				System.out.println("client registered ( " + clientIP + ", " + clientport+ " )");
			}	
			if(portSet.contains(clientport) && !clientMessage.equals("#REG")) {
				if(clientMessage.equals("#DEREG")) {
					System.out.println("client de-registered ( " + clientIP + ", " + clientport+ " )");         
					byte[] sendData  = new byte[1024];
					sendData = clientMessage.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientport);         
					udpServerSocket.send(sendPacket); 
					portSet.remove(clientport);
				}else 
				{
					byte[] sendData  = new byte[1024];
		 
					sendData = clientMessage.getBytes();
					System.out.println("reveived ( " + clientMessage + " )" + " and echoed to " + portSet.size() + " clients");
					for(Integer port : portSet) 
					{
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);           
							udpServerSocket.send(sendPacket);    
					}
				}
			}
			else {
				if(!clientMessage.equals("#REG")) {
					System.out.println("no clients to echo");
				}	
			}	
        }
    }
}
