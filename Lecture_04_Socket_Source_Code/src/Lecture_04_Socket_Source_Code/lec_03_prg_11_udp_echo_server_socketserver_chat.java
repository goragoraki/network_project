package Lecture_04_Socket_Source_Code;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class lec_03_prg_11_udp_echo_server_socketserver_chat {
	
	public static void main(String[] args) {
		ArrayList<DatagramPacket> list = new ArrayList<>();
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
				list.add(rcvP);
				String msg = "";
				for(DatagramPacket sndP: list) {
					msg = new String(sndP.getData(),0,sndP.getLength());
					sendP = new DatagramPacket(sndP.getData(), sndP.getData().length, 
							sndP.getAddress(), sndP.getPort());
					datagramSocket.send(sendP);
				}
				System.out.println("> echoed: " + msg);
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("> echo-server is de-activated");
		}
	}
}
