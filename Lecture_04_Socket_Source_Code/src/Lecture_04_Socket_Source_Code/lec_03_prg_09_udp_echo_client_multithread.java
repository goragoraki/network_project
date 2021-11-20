package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.InputStreamReader;

public class lec_03_prg_09_udp_echo_client_multithread {
	public static void main(String args[]) {
		DatagramSocket datagramSocket = null;
		DatagramPacket rcvP = null;
		DatagramPacket sendP = null;
		InetAddress inetAddress = null;
		BufferedReader br = null;
		int PORT = 65456;
		String IP = "127.0.0.1";
		try {
			inetAddress = InetAddress.getByName(IP);
			datagramSocket = new DatagramSocket();
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("> echo-cilent is activated");
			while(true) {
				String msg = "";
				System.out.print("> ");
				msg = br.readLine();
				sendP = new DatagramPacket(msg.getBytes(), msg.getBytes().length, inetAddress, PORT);
				datagramSocket.send(sendP);
				if (msg.equals("quit")) {
					break;
				}
				byte[] buffer = new byte[msg.getBytes().length];
				rcvP = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(rcvP);
				
				String rcvM = new String(rcvP.getData(),0,rcvP.getData().length);
				System.out.println("> received: " + rcvM);
			}
			System.out.println("> echo-cilent is de-activated");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}