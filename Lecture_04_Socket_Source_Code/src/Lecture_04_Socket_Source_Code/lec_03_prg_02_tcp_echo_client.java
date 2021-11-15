package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class lec_03_prg_02_tcp_echo_client {
	public static void main(String args[]) {
		String ip = "127.0.0.1";
		int port = 65456;
		System.out.println("> echo-cilent is activated");
		Socket mSocket;

		BufferedReader recv;				
		PrintWriter pwSend;					
		BufferedReader in; 		
		try {
			mSocket = new Socket(ip, port);
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

		} 
	}

}
