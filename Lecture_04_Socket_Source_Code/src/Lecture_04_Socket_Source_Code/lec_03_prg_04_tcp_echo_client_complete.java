package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class lec_03_prg_04_tcp_echo_client_complete {
	public static void main(String args[]) {
		String ip = "127.0.0.1";
		int port = 65456;
		System.out.println("> echo-cilent is activated");
		Socket mSocket;

		BufferedReader recv;				
		PrintWriter pwSend;					
		BufferedReader in;
		
		SocketAddress socketAddress = new InetSocketAddress(ip, port);
		mSocket = new Socket();
		try {
			mSocket.connect(socketAddress, 20000);
		}catch(SocketException e) {
			System.out.println("> connect() failed and program terminated");
		}catch(Exception e) {
			System.out.print("> connect() failed by execption: ");
			e.printStackTrace();
		}
		try {
			recv = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			pwSend = new PrintWriter(mSocket.getOutputStream());
			in= new BufferedReader(new InputStreamReader(System.in));
			
			while (true) {
				System.out.print("> ");
				String s = in.readLine();		
				pwSend.println(s);
				pwSend.flush();
				if (s.equals("quit")) {						
					break;
				}
				System.out.println("> recevied: " + recv.readLine());		
			}

			mSocket.close();
			System.out.println("> echo-client is de-activated");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
