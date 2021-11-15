package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class lec_03_prg_01_tcp_echo_server {
	public static void main(String args[]) {
		
		ServerSocket mServerSocket;
		Socket mSocket;

		BufferedReader recv; 
		PrintWriter pwSend;

		try {
			mServerSocket = new ServerSocket(65456); 
			System.out.println("> echo-server is activated");
			mSocket = mServerSocket.accept();
			System.out.println("> client connected by IP address "
					+ mSocket.getInetAddress() + " with Port number " + mSocket.getPort());
			
			recv = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
			pwSend = new PrintWriter(mSocket.getOutputStream());
			while (true) {
				String strRecv = recv.readLine();		
				System.out.println("> echoed : " + strRecv);

				if (strRecv.equals("quit")) {
					break;
				}

				pwSend.println(strRecv);	
				pwSend.flush();
			}
			mSocket.close();
			mServerSocket.close();
			System.out.println("> echo-server is de-activated");
		} catch (IOException e) {}
	}
}
