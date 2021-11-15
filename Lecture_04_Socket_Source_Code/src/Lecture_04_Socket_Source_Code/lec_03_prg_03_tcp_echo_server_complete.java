package Lecture_04_Socket_Source_Code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class lec_03_prg_03_tcp_echo_server_complete {
	public static void main(String args[]) {
		String ip = "127.0.0.1";
		int port = 65456;
		ServerSocket mServerSocket;
		Socket mSocket;

		BufferedReader recv; 
		PrintWriter pwSend;

		try {
			mServerSocket = new ServerSocket();
			mServerSocket.bind(new InetSocketAddress(ip, port), 20000);
		}catch(SocketTimeoutException ste) {
			System.out.println("> listen() failed and program terminated");
			return;
		}catch(IOException e) {
			System.out.println("> bind() failed and program terminated");
			return;
		}catch(Exception e) {
			System.out.print("> bind() failed by exception :");
			e.printStackTrace();
			return;
		}
		try {
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
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
