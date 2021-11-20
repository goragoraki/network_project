package Lecture_04_Socket_Source_Code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class lec_03_prg_10_udp_echo_server_socketserver {
	public static void main(String args[]) {
		DatagramSocket datagramSocket = null;
		DatagramPacket rcvP =null;
		DatagramPacket sendP = null;
		int PORT = 65456;
		try {
			datagramSocket = new DatagramSocket(PORT);
			System.out.println("> echo-server is activated");
			while(true) {
				byte[] buffer = new byte[1024];
				rcvP = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(rcvP);
				String msg = new String(rcvP.getData(),0,rcvP.getLength());
				System.out.println("> echoed: " + msg);
				sendP = new DatagramPacket(rcvP.getData(), rcvP.getData().length, 
						rcvP.getAddress(), rcvP.getPort());
				datagramSocket.send(sendP);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
