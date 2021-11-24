package Lecture_04_Socket_Source_Code;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class lec_03_prg_10_udp_echo_server_socketserver {
	public static void main(String args[]) throws Exception {
		int severport = 65456;
		DatagramSocket udpServerSocket = new DatagramSocket(severport);
		
		try {	
			System.out.println("> echo-server is activated");
			while(true) {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				udpServerSocket.receive(receivePacket);           
				String clientMessage = (new String(receivePacket.getData())).trim();
				InetAddress clientIP = receivePacket.getAddress();           
				int clientport = receivePacket.getPort();
				System.out.println("> echoed: " + clientMessage);
				byte[] sendData  = new byte[1024];
				sendData = clientMessage.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientport);         
				udpServerSocket.send(sendPacket);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
